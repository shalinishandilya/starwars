package com.shalini.starwars.service;

import com.shalini.starwars.constants.ApplicationConstants;
import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.repository.InMemoryStarWarsRepository;
import entity.*;
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

    @CircuitBreaker(name = ApplicationConstants.CIRCUIT_BREAKER_INSTANCE_STAR_WARS_DATA,
            fallbackMethod = ApplicationConstants.CIRCUIT_BREAKER_FALLBACK_FIND_STAR_WARS_DATA)
    public Optional<List<StarWarsEntity>> findByTypeAndName(String type, String name, boolean offlineMode) throws NoDataFoundException {
        log.info("Finding Star Wars entities by type: {} and name: {} with offlineMode: {}", type, name, offlineMode);

        List<StarWarsEntity> entities = new ArrayList<>();
        if (offlineMode) {
            log.info("Offline mode is enabled. Fetching from repository.");
            return repository.findByTypeAndName(type, name);
        } else {
            String url = constructSwapsAPIURL(type, name);
            boolean hasNextPage = true;
            while (hasNextPage) {
                log.info("Making a request to SWAPI with URL: {}", url);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
                    JSONArray results = jsonResponse.getJSONArray("results");

                    StarWarsEntityFactory factory = getFactory(type);

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        StarWarsEntity entity = factory.createEntity(result, type);
                        entity.setType(type);
                        if (type.equals("films")) {
                            entity.setName(result.getString("title"));
                        } else {
                            entity.setName(result.getString("name"));
                        }
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
            addDataToCache(entities);
        }
        log.info("Found {} entities", entities.size());
        return Optional.of(entities);
    }

    private static String constructSwapsAPIURL(String type, String name) {
        String url = SWAPI_BASE_URL;
        if (type != null) {
            url += type + "/";
        }
        if (name != null) {
            url += "?search=" + name;
        }
        return url;
    }

    private void addDataToCache(List<StarWarsEntity> entities) {
        for(StarWarsEntity entity: entities) {
            repository.addEntity(entity);
        }
    }

    private Optional<List<StarWarsEntity>> findStarWarsData(Throwable throwable) throws Throwable {
        log.info("Fallback method triggered due to: {}", throwable.getMessage());
        return repository.findAll();
    }

    private StarWarsEntityFactory getFactory(String entityType) {
        return switch (entityType) {
            case "planets" -> new PlanetsEntityFactory();
            case "vehicles" -> new VehiclesEntityFactory();
            case "films" -> new FilmsEntityFactory();
            case "species" -> new SpeciesEntityFactory();
            case "starships" -> new StarshipEntityFactory();
            case "people" -> new PeopleEntityFactory();
            default -> null;
        };
    }

}
