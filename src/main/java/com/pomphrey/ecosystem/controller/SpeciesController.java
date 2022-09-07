package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.model.Species;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeciesController {

    @GetMapping("/species/{name}")
    public ResponseEntity<Species> getSpeciesDetails(@PathVariable(value = "name") String name) {
        //todo add exception handling
        //todo retrieve data
        Species species = new Species("wolf", "c", "rabbit", 50, 1, 12, 0.3, 0.3, 4, 1, 2);
        ResponseEntity responseEntity = new ResponseEntity<>(species, HttpStatus.OK);
        return responseEntity;
    }
}
