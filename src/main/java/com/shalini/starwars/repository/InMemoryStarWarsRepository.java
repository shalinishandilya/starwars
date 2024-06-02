package com.shalini.starwars.repository;

import com.shalini.starwars.exception.NoDataFoundException;
import entity.StarWarsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class InMemoryStarWarsRepository {
    private Map<String, Map<String, StarWarsEntity>> dataStore = new HashMap<>();
    private static InMemoryStarWarsRepository instance;

    private InMemoryStarWarsRepository() {
        dataStore = new HashMap<>();
    }

    public static synchronized InMemoryStarWarsRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryStarWarsRepository();
            log.info("Created a new instance of InMemoryStarWarsRepository");
        }
        return instance;
    }

    public void addEntity(StarWarsEntity entity) {
        dataStore.computeIfAbsent(entity.getType(), k -> new HashMap<>())
                .put(entity.getName().toLowerCase(), entity);
    }

    public Optional<List<StarWarsEntity>> findAll() {
        log.info("Fetching all entities");
        List<StarWarsEntity> allEntities = dataStore.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .collect(Collectors.toList());

        log.debug("All entities fetched: {}", allEntities);
        return allEntities.isEmpty() ? Optional.empty() : Optional.of(allEntities);
    }

    public Optional<List<StarWarsEntity>> findByTypeAndName(String type, String name)
            throws NoDataFoundException {
        log.info("Fetching entities by type: {} and name: {}", type, name);
        if (type == null) {
            log.error("Type or name is null. Throwing NoDataFoundException");
            throw new NoDataFoundException("No Data in Cache");
        }
        if (name == null) {
            return findAll();
        }
        Optional<StarWarsEntity> entity =
                Optional.ofNullable(dataStore.getOrDefault(type, new HashMap<>()).get(name.toLowerCase()));

        if (entity.isPresent()) {
            log.info("Entity found: {}", entity.get());
            return Optional.of(Collections.singletonList(entity.get()));
        } else {
            log.warn("No entity found for type: {} and name: {}", type, name);
            return Optional.empty();
        }
    }
}
