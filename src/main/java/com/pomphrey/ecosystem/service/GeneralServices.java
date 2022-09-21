package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.model.configuration.Species;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class GeneralServices {

    public static List<Integer> calculateAgeSpread(Species species, int count){
        if(count> species.getLifeExpectancy()){
            return calculateAgesCountGreaterThanLifeExpectancy(species, count);
        } else {
            return calculateAgesCountLessThanLifeExpectancy(species, count);
        }
    }

    private static List<Integer> calculateAgesCountGreaterThanLifeExpectancy(Species species, int count){
        int pointer = 0;
        List ageList = new ArrayList<>();
        for(int i=0; i < count; i++){
            ageList.add(pointer);
            pointer ++;
            if(pointer>species.getLifeExpectancy()){
                pointer = 0;
            }
        }
        return ageList;
    }

    private static List<Integer> calculateAgesCountLessThanLifeExpectancy(Species species, int count){
        int increment = species.getLifeExpectancy() / count;
        int addedCount = 0;
        List ageList = new ArrayList<>();
        for(int i=increment; i <= species.getLifeExpectancy() && addedCount < count; i = i + increment){
            ageList.add(i);
            addedCount ++;
        }
        return ageList;
    }


}
