package com.shalini.starwars.controller;

import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.service.StarWarsService;
import entity.PlanetsEntity;
import entity.StarWarsEntity;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.shalini.starwars.constants.ApplicationConstants.NO_DATA_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class StarWarsControllerTest {

    @Mock
    private StarWarsService starWarsService;

    @InjectMocks
    private StarWarsController starWarsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEntity_Success() throws NoDataFoundException, BadRequestException {

        List<StarWarsEntity> entities = Collections.singletonList(new PlanetsEntity(new PlanetsEntity.Builder()));


        when(starWarsService.findByTypeAndName("type", "name", false)).thenReturn(Optional.of(entities));

        ResponseEntity<List<StarWarsEntity>> response = starWarsController.getEntity("type", "name", false);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entities, response.getBody());
    }

    @Test
    public void testGetEntity_NoDataFound() throws NoDataFoundException {
        when(starWarsService.findByTypeAndName("type", "name", false)).thenReturn(Optional.empty());

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> {
            starWarsController.getEntity("type", "name", false);
        });

        assertEquals(NO_DATA_FOUND, exception.getMessage());
    }

    @Test
    public void testGetEntity_BadRequest() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            starWarsController.getEntity(null, "name", false);
        });

        assertEquals("Resource Not Found", exception.getMessage());
    }
}
