package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.dao.SpeciesDao;
import com.pomphrey.ecosystem.exception.DataIntegrityException;
import com.pomphrey.ecosystem.model.Species;
import com.pomphrey.ecosystem.service.CarnivoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpeciesController {

    @Autowired
    SpeciesDao speciesDao;

    @Autowired
    CarnivoreServices carnivoreServices;

    @GetMapping("/species/{name}")
    public ResponseEntity getSpeciesDetails(@PathVariable(value = "name") String name) {
        ResponseEntity responseEntity = null;
        try {
            Species species = speciesDao.querySingleSpecies(name);
            responseEntity = new ResponseEntity<>(species, HttpStatus.OK);
        }
        catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
            if(ex.getMessage().contains("Species Not Found")){
                responseEntity = new ResponseEntity<>("Species Not Found", HttpStatus.BAD_REQUEST);
            } else {
                responseEntity = new ResponseEntity<>("Unhandled error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return responseEntity;
    }

    @PostMapping("/species/add")
    public ResponseEntity<String> addSpecies(@RequestBody Species species){
        String responseText = "OK";
        try{
            if(species.getType().equalsIgnoreCase("C")) {
                carnivoreServices.checkDataIntegrity(species);
            }
            speciesDao.insertSpecies(species);
        }
        catch(DataIntegrityException ex){
            responseText = ex.toString();
        }
        catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
            if(ex.getMessage().contains("Unique index or primary key violation")){
                responseText = "Record already exists";
            } else {
                responseText = "Unhandled error";
            }
        }
        ResponseEntity responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/species/update")
    public ResponseEntity<String> updateSpecies(@RequestBody Species species){
        String responseText = "OK";
        try{
            if(species.getType().equalsIgnoreCase("C")) {
                carnivoreServices.checkDataIntegrity(species);
            }
            speciesDao.deleteSpecies(species);
            speciesDao.insertSpecies(species);
        }
        catch(DataIntegrityException ex){
            responseText = ex.toString();
        }
        catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
            if(ex.getMessage().contains("Unique index or primary key violation")){
                responseText = "Record already exists";
            } else {
                responseText = "Unhandled error";
            }
        }
        ResponseEntity responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        return responseEntity;
    }

}
