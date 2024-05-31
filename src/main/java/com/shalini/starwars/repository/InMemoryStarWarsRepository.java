package com.shalini.starwars.repository;

import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.model.StarWarsEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryStarWarsRepository {
    private final Map<String, Map<String, StarWarsEntity>> dataStore = new HashMap<>();
    private static InMemoryStarWarsRepository instance;

    private Map<String, List<StarWarsEntity>> data;

    private InMemoryStarWarsRepository() {
        data = new HashMap<>();
        addEntity(new StarWarsEntity.Builder()
                .setType("planets")
                .setName("Tatooine")
                .setRotationPeriod("23")
                .setOrbitalPeriod("304")
                .setDiameter("10465")
                .setClimate("arid")
                .setGravity("1 standard")
                .setTerrain("desert")
                .setSurfaceWater("1")
                .setPopulation("200000")
                .setResidents(List.of("https://swapi.dev/api/people/1/"))
                .setFilms(List.of("https://swapi.dev/api/films/1/"))
                .build());
    }

    public static synchronized InMemoryStarWarsRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryStarWarsRepository();
        }
        return instance;
    }

    public void addEntity(StarWarsEntity entity) {
        dataStore.computeIfAbsent(entity.getType(), k -> new HashMap<>())
                .put(entity.getName().toLowerCase(), entity);
    }

    public Optional<List<StarWarsEntity>> findAll() {
        List<StarWarsEntity> allEntities = dataStore.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .collect(Collectors.toList());

        return allEntities.isEmpty() ? Optional.empty() : Optional.of(allEntities);
    }


    public Optional<List<StarWarsEntity>> findByTypeAndName(String type, String name)
            throws NoDataFoundException {
        if (type == null || name == null) {
            throw new NoDataFoundException("No Data in Cache");
        }
        return Optional.of(Collections.singletonList(dataStore.getOrDefault(type, new HashMap<>())
                .get(name.toLowerCase())));
    }
}
