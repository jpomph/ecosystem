package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.model.worldstate.Individual;
import com.pomphrey.ecosystem.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralServices {

    @Autowired
    SpeciesRepository speciesRepository;

    public void processSingleYear(List<Individual> individuals) {
        ageAllIndividualsOneYear(individuals);
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
        for(int i=0; i<deadList.size(); i++){
            individuals.remove(i);
        }
    }

}
