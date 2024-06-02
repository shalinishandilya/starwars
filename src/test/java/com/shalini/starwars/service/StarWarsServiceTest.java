package com.shalini.starwars.service;

import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.repository.InMemoryStarWarsRepository;
import entity.PlanetsEntity;
import entity.StarWarsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StarWarsServiceTest {

    @InjectMocks
    private StarWarsService starWarsService;

    @Mock
    private InMemoryStarWarsRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByTypeAndNameOfflineMode() throws NoDataFoundException {
        StarWarsEntity entity = new PlanetsEntity.Builder()
                .setType("planets")
                .setName("Tatooine")
                .build();
        when(repository.findByTypeAndName("planets", "Tatooine")).thenReturn(Optional.of(Collections.singletonList(entity)));

        Optional<List<StarWarsEntity>> result = starWarsService.findByTypeAndName("planets", "Tatooine", true);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("Tatooine", result.get().get(0).getName());
    }

    @Test
    public void testFindByTypeAndNameOnlineMode() throws NoDataFoundException {
        String jsonResponse = "{ \"results\": [ { \"name\": \"Tatooine\", \"rotation_period\": \"23\", \"orbital_period\": \"304\", \"diameter\": \"10465\", \"climate\": \"arid\", \"gravity\": \"1 standard\", \"terrain\": \"desert\", \"surface_water\": \"1\", \"population\": \"200000\", \"residents\": [ \"https://swapi.dev/api/people/1/\" ], \"films\": [ \"https://swapi.dev/api/films/1/\" ], \"created\": \"2014-12-09T13:50:49.641000Z\", \"edited\": \"2014-12-20T20:58:18.411000Z\", \"url\": \"https://swapi.dev/api/planets/1/\" } ] }";
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class))).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        Optional<List<StarWarsEntity>> result =
                starWarsService.findByTypeAndName("planets", "Tatooine", false);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("Tatooine", result.get().get(0).getName());
    }
}
