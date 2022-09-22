package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.model.worldstate.Ecosystem;
import com.pomphrey.ecosystem.model.worldstate.Individual;
import com.pomphrey.ecosystem.model.worldstate.Summary;
import com.pomphrey.ecosystem.model.worldstate.SummaryKey;
import com.pomphrey.ecosystem.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeneralServices {

    @Autowired
    SpeciesRepository speciesRepository;

    public void processSingleYear(List<Individual> individuals, Ecosystem ecosystem) {
        ageAllIndividualsOneYear(individuals);
        ecosystem.setDate(ecosystem.getDate().plusYears(1));
    }

    public void ageAllIndividualsOneYear(List<Individual> individuals){
        List<Integer> deadList = new ArrayList<>();
        for(int i=0; i<individuals.size(); i++){
            if(individuals.get(i).getAge() >= (speciesRepository.findByName(individuals.get(i).getSpecies()).getLifeExpectancy()-1)){
                deadList.add(i);
            } else {
                individuals.get(i).setAge(individuals.get(i).getAge()+1);
            }
        }
        for(int i=deadList.size()-1; i>-1; i--){
            int indexToRemove = deadList.get(i);
            individuals.remove(indexToRemove);
        }
    }

    public List<Summary> generateSummary(List<Individual> individuals, Ecosystem ecosystem){

        List<Summary> speciesSummary = new ArrayList<>();

        Map<String, List<Individual>> groupedIndividuals = individuals.stream()
                .collect(Collectors.groupingBy(Individual::getSpecies));

        groupedIndividuals.forEach((species, list)->{
            speciesSummary.add(new Summary(new SummaryKey(ecosystem.getDate(), species), list.size()));
        });

        return speciesSummary;
    }

}
