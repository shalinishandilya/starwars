package com.shalini.starwars.service;

import com.shalini.starwars.constants.ApplicationConstants;
import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.model.StarWarsEntity;
import com.shalini.starwars.repository.InMemoryStarWarsRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class StarWarsService {

    @Autowired
    private InMemoryStarWarsRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SWAPI_BASE_URL = "https://swapi.dev/api/";

    private List<String> getUrls(JSONArray jsonArray) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            urls.add(jsonArray.getString(i));
        }
        return urls;
    }

    @CircuitBreaker(name = ApplicationConstants.CIRCUIT_BREAKER_INSTANCE_STAR_WARS_DATA,
            fallbackMethod = ApplicationConstants.CIRCUIT_BREAKER_FALLBACK_FIND_STAR_WARS_DATA)
    public Optional<List<StarWarsEntity>> findByTypeAndName(String type, String name, boolean offlineMode) throws NoDataFoundException {
        log.info("Finding Star Wars entities by type: {} and name: {} with offlineMode: {}", type, name, offlineMode);

        List<StarWarsEntity> entities = new ArrayList<>();
        if (offlineMode) {
            log.info("Offline mode is enabled. Fetching from repository.");
            return repository.findByTypeAndName(type, name);
        } else {
            String url = SWAPI_BASE_URL;
            if (type != null) {
                url += type + "/";
            }
            if (name != null) {
                url += "?search=" + name;
            }
            boolean hasNextPage = true;
            while (hasNextPage) {
                log.info("Making a request to SWAPI with URL: {}", url);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
                    JSONArray results = jsonResponse.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        StarWarsEntity entity = new StarWarsEntity.Builder()
                                .setType(type)
                                .setName(result.getString("name"))
                                .setRotationPeriod(result.getString("rotation_period"))
                                .setOrbitalPeriod(result.getString("orbital_period"))
                                .setDiameter(result.getString("diameter"))
                                .setClimate(result.getString("climate"))
                                .setGravity(result.getString("gravity"))
                                .setTerrain(result.getString("terrain"))
                                .setSurfaceWater(result.getString("surface_water"))
                                .setPopulation(result.getString("population"))
                                .setResidents(getUrls(result.getJSONArray("residents")))
                                .setFilms(getUrls(result.getJSONArray("films")))
                                .setCreated(result.getString("created"))
                                .setEdited(result.getString("edited"))
                                .setUrl(result.getString("url"))
                                .build();
                        entities.add(entity);
                    }
                    String nextUrl = jsonResponse.optString("next");
                    if (nextUrl != null && !nextUrl.isEmpty()) {
                        url = nextUrl;
                    } else {
                        hasNextPage = false;
                    }
                    log.info("Next URL: {}", nextUrl);
                } else {
                    log.error("Failed to fetch data from SWAPI. Status code: {}", responseEntity.getStatusCode());
                    hasNextPage = false;
                }
            }
        }
        log.info("Found {} entities", entities.size());
        return Optional.of(entities);
    }

    private Optional<List<StarWarsEntity>> findStarWarsData(Throwable throwable) throws Throwable {
        log.info("Fallback method triggered due to: {}", throwable.getMessage());
        return repository.findAll();
    }
}
