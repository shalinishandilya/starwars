package com.shalini.starwars.controller;

import com.shalini.starwars.exception.NoDataFoundException;
import com.shalini.starwars.service.StarWarsService;
import entity.StarWarsEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.shalini.starwars.constants.ApplicationConstants.NO_DATA_FOUND;

@RestController
@RequestMapping("/api/v1/")
public class StarWarsController {

    @Autowired
    private StarWarsService starWarsService;

    @GetMapping("/starwars")
    @Operation(summary = "Star Wars API",
            description = "Operations for retrieving Star Wars entities and can be filtered by type or name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of Data"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters provided"),
            @ApiResponse(responseCode = "404", description = "No Data found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<StarWarsEntity>> getEntity(@RequestParam(required = true) String type,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) boolean offlineMode)
            throws NoDataFoundException, BadRequestException {
        if (type == null) {
            throw new BadRequestException("Resource Not Found");
        }
        Optional<List<StarWarsEntity>> starWarsServiceByTypeAndName =
                starWarsService.findByTypeAndName(type, name, offlineMode);
        if (starWarsServiceByTypeAndName.isEmpty()) {
            throw new NoDataFoundException(NO_DATA_FOUND);
        }

        return ResponseEntity.ok(starWarsServiceByTypeAndName.get());
    }
}
