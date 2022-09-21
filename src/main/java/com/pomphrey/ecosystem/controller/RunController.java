package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.dao.IndividualRepository;
import com.pomphrey.ecosystem.dao.InitialConditionRepository;
import com.pomphrey.ecosystem.dao.SpeciesRepository;
import com.pomphrey.ecosystem.model.configuration.InitialCondition;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.model.worldstate.Individual;
import com.pomphrey.ecosystem.service.GeneralServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RunController {

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    InitialConditionRepository conditionRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    @GetMapping("/ecosystem/create")
    public ResponseEntity createEcosystem() {
        //delete any existing ecosystem
        individualRepository.deleteAll();
        //get all sepecies from initial conditions table
        conditionRepository.findAll().forEach(condition -> {
            Species species = speciesRepository.findByName(condition.getSpecies());
            List<Integer> ageList = GeneralServices.calculateAgeSpread(species, condition.getIndividualCount());
            for(int i=0; i<condition.getIndividualCount(); i++){
                individualRepository.save(new Individual(condition.getSpecies(),ageList.get(i)));
            }
        });
        return new ResponseEntity("Ecosystem generated", HttpStatus.OK);
    }

    @GetMapping("/ecosystem/query")
    public ResponseEntity queryEcosystem() {
        List<Individual> individuals = individualRepository.findAll();
        return new ResponseEntity(individuals, HttpStatus.OK);
    }


}
