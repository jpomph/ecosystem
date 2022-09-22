package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.model.worldstate.Ecosystem;
import com.pomphrey.ecosystem.repository.EcosystemRepository;
import com.pomphrey.ecosystem.repository.IndividualRepository;
import com.pomphrey.ecosystem.repository.InitialConditionRepository;
import com.pomphrey.ecosystem.repository.SpeciesRepository;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.model.worldstate.Individual;
import com.pomphrey.ecosystem.service.GeneralServices;
import com.pomphrey.ecosystem.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RunController {

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    InitialConditionRepository conditionRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    EcosystemRepository ecosystemRepository;

    @Autowired
    GeneralServices generalServices;

    @GetMapping("/ecosystem/create")
    public ResponseEntity createEcosystem() {
        //delete any existing ecosystem
        individualRepository.deleteAll();
        //create the ecosystem
        ecosystemRepository.save(new Ecosystem());
        //get all species from initial conditions table and create the corresponding number of individuals
        conditionRepository.findAll().forEach(condition -> {
            Species species = speciesRepository.findByName(condition.getSpecies());
            List<Integer> ageList = GeneralUtils.calculateAgeSpread(species, condition.getIndividualCount());
            for(int i=0; i<condition.getIndividualCount(); i++){
                individualRepository.save(new Individual(condition.getSpecies(),ageList.get(i)));
            }
        });
        return new ResponseEntity("Ecosystem generated", HttpStatus.OK);
    }

    @GetMapping("/ecosystem/query/individuals")
    public ResponseEntity queryEcosystem() {
        List<Individual> individuals = individualRepository.findAll();
        return new ResponseEntity(individuals, HttpStatus.OK);
    }

    @GetMapping("/ecosystem/query/date")
    public ResponseEntity queryEcosystemDate() {
        LocalDate date = ecosystemRepository.findAll().get(0).getDate();
        return new ResponseEntity(date, HttpStatus.OK);
    }

    @GetMapping("/ecosystem/process/year")
    public ResponseEntity processOneYear() {
        List<Individual> individuals = individualRepository.findAll();
        generalServices.processSingleYear(individuals);
        individualRepository.deleteAll();
        individualRepository.saveAll(individuals);
        return new ResponseEntity("Processed 1 year", HttpStatus.OK);
    }




}
