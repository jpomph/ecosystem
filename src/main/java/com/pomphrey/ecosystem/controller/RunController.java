package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.exception.EcosystemNotExistException;
import com.pomphrey.ecosystem.model.worldstate.Ecosystem;
import com.pomphrey.ecosystem.model.worldstate.Population;
import com.pomphrey.ecosystem.model.worldstate.Summary;
import com.pomphrey.ecosystem.repository.*;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.model.worldstate.Individual;
import com.pomphrey.ecosystem.service.EcosystemService;
import com.pomphrey.ecosystem.service.GeneralServices;
import com.pomphrey.ecosystem.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RunController {

    @Autowired
    EcosystemService ecosystemService;

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

    @Autowired
    SummaryRepository summaryRepository;

    @GetMapping("/ecosystem/create")
    public ResponseEntity createEcosystem() {

        ecosystemService.initialiseEcosystem();

        return new ResponseEntity("Ecosystem generated", HttpStatus.OK);
    }

    @GetMapping("/ecosystem/query/individuals")
    public ResponseEntity queryEcosystem() {
        List<Individual> individuals = individualRepository.findAll();
        return new ResponseEntity(individuals, HttpStatus.OK);
    }

    @GetMapping("/ecosystem/query/date")
    public ResponseEntity queryEcosystemDate() throws EcosystemNotExistException {
        LocalDate date = ecosystemService.getEcosystem().getDate();
        return new ResponseEntity(date, HttpStatus.OK);
    }

    @GetMapping("/ecosystem/process/year")
    public ResponseEntity processOneYear() {
        ecosystemService.processOneYear();

        return new ResponseEntity(summaryRepository.findAll(), HttpStatus.OK);
    }




}
