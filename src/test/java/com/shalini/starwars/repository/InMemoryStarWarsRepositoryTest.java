package com.shalini.starwars.repository;

import com.shalini.starwars.exception.NoDataFoundException;
import entity.PlanetsEntity;
import entity.StarWarsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStarWarsRepositoryTest {

    private InMemoryStarWarsRepository repository;

    @BeforeEach
    public void setUp() {
        repository = InMemoryStarWarsRepository.getInstance();
    }

    @Test
    public void testAddEntity() throws NoDataFoundException {
        StarWarsEntity entity = new PlanetsEntity.Builder()
                .setType("characters")
                .setName("Luke Skywalker")
                .build();

        repository.addEntity(entity);

        Optional<List<StarWarsEntity>> result = repository.findByTypeAndName("characters", "Luke Skywalker");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("Luke Skywalker", result.get().get(0).getName());
    }

    @Test
    public void testFindAll() {
        Optional<List<StarWarsEntity>> result = repository.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByTypeAndName() throws NoDataFoundException {
        Optional<List<StarWarsEntity>> result = repository.findByTypeAndName("planets", "Tatooine");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByTypeAndNameNotFound() {
        assertThrows(NoDataFoundException.class, () -> repository.findByTypeAndName(null, null));
    }

    @Test
    public void testFindByTypeAndNameWithNullValues() {
        assertThrows(NoDataFoundException.class, () -> repository.findByTypeAndName(null, "Tatooine"));
    }
}
