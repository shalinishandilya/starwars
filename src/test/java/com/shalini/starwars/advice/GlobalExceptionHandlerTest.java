package com.shalini.starwars.advice;

import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.exception.StarWarsError;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleCityNotFoundException() {
        NoDataFoundException exception = new NoDataFoundException("No data found");
        ResponseEntity<StarWarsError> responseEntity = globalExceptionHandler.handleCityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
        assertEquals("No data found", responseEntity.getBody().getMessage());
    }

    @Test
    public void testHandleBadRequestException() {
        BadRequestException exception = new BadRequestException("Bad request");
        ResponseEntity<StarWarsError> responseEntity = globalExceptionHandler.handleBadRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatus());
        assertEquals("Bad request", responseEntity.getBody().getMessage());
    }
}
