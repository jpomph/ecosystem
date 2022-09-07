package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.dao.SpeciesDao;
import com.pomphrey.ecosystem.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SpeciesController {

    @Autowired
    SpeciesDao speciesDao;

    @GetMapping("/species/{name}")
    public ResponseEntity<Species> getSpeciesDetails(@PathVariable(value = "name") String name) {
        //todo add exception handling
        Species species = speciesDao.querySingleSpecies(name);
        ResponseEntity responseEntity = new ResponseEntity<>(species, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/species/add")
    public ResponseEntity<String> addSpecies(@RequestBody Species species){
        speciesDao.insertSpecies(species);
        ResponseEntity responseEntity = new ResponseEntity<>("OK", HttpStatus.OK);
        return responseEntity;
    }

}
