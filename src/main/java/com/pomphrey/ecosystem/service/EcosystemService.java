package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.exception.EcosystemNotExistException;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.model.worldstate.Ecosystem;
import com.pomphrey.ecosystem.model.worldstate.Individual;
import com.pomphrey.ecosystem.model.worldstate.Population;
import com.pomphrey.ecosystem.model.worldstate.Summary;
import com.pomphrey.ecosystem.repository.*;
import com.pomphrey.ecosystem.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcosystemService {

    @Autowired
    EcosystemRepository ecosystemRepository;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    InitialConditionRepository conditionRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    GeneralServices generalServices;

    @Autowired
    SummaryRepository summaryRepository;

    public Ecosystem getEcosystem() throws EcosystemNotExistException{
        List<Ecosystem> ecosystems  = ecosystemRepository.findAll();
        if (ecosystems.size()==0){
            throw new EcosystemNotExistException();
        }
        return ecosystems.get(0);
    }

    public boolean isEcosystemExists(){
        List<Ecosystem> ecosystems  = ecosystemRepository.findAll();
        return (!ecosystems.isEmpty());
    }

    public void initialiseEcosystem(){
        //delete any existing ecosystem
        individualRepository.deleteAll();
        //create the ecosystem
        Ecosystem ecosystem = new Ecosystem();
        //get all species from initial conditions table and create the corresponding number of individuals
        conditionRepository.findAll().forEach(condition -> {
            Species species = speciesRepository.findByName(condition.getSpecies());
            Population population = new Population(species, ecosystem);
            List<Integer> ageList = GeneralUtils.calculateAgeSpread(species, condition.getIndividualCount());
            for(int i=0; i<condition.getIndividualCount(); i++){
                population.addIndividual(new Individual(condition.getSpecies(),ageList.get(i), population));
            }
            ecosystem.addPopulation(population);
        });
        ecosystemRepository.save(ecosystem);
    }

    public void processOneYear(){
        List<Individual> individuals = individualRepository.findAll();
        Ecosystem ecosystem = ecosystemRepository.findAll().get(0);
        generalServices.processSingleYear(individuals, ecosystem);
        ecosystemRepository.save(ecosystem);
        individualRepository.deleteAll();
        individualRepository.saveAll(individuals);

        //Generate summary
        List<Summary> summary = generalServices.generateSummary(individuals, ecosystem);
        summaryRepository.saveAll(summary);

        return;
    }

}
