package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.config.Constants;
import com.pomphrey.ecosystem.repository.SpeciesRepository;
import com.pomphrey.ecosystem.exception.DataIntegrityException;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.util.CarnivoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class SpeciesController {

    @Autowired
    CarnivoreUtils carnivoreUtils;

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    Constants constants;

    @GetMapping("/species/{name}")
    public ResponseEntity getSpeciesDetails(@PathVariable(value = "name") String name) {
        ResponseEntity responseEntity = null;
        Species species = speciesRepository.findByName(name);
        if(species==null){
            responseEntity = new ResponseEntity<>("Species not found", HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(species, HttpStatus.OK);
        }
        return responseEntity;
    }

    @PostMapping("/species/delete/{name}")
    @Transactional
    public ResponseEntity deleteSpeciesDetails(@PathVariable(value = "name") String name) {
        ResponseEntity responseEntity = null;
        speciesRepository.deleteByName(name);
        responseEntity = new ResponseEntity<>("Species deleted", HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/species/add")
    public ResponseEntity<String> addSpecies(@RequestBody Species species){
        ResponseEntity responseEntity = null;
        String responseText = "Species added";
        if(speciesRepository.findByName(species.getName())!=null){
            responseEntity = new ResponseEntity<>(constants.SPECIES_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        } else {
            try {
                if (species.getType().equalsIgnoreCase("C")) {
                    carnivoreUtils.checkDataIntegrity(species);
                }
                speciesRepository.save(species);
            } catch (DataIntegrityException ex) {
                responseText = ex.toString();
            } catch (Exception ex) {
                responseText = ex.toString();
            }
            responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        }

        return responseEntity;

    }

    @PostMapping("/species/update")
    public ResponseEntity<String> updateSpecies(@RequestBody Species species){
        ResponseEntity responseEntity = null;
        if(speciesRepository.findByName(species.getName())==null) {
            responseEntity = new ResponseEntity<>(constants.SPECIES_DOESNT_EXIST,HttpStatus.BAD_REQUEST);
        } else {
            try {
                if (species.getType().equalsIgnoreCase("C")) {
                    carnivoreUtils.checkDataIntegrity(species);
                }
                speciesRepository.save(species);
                responseEntity = new ResponseEntity<>(constants.SPECIES_UPDATED,HttpStatus.OK);
            } catch (DataIntegrityException ex) {
                responseEntity = new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        return responseEntity;
    }

}
