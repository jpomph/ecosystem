package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.utils.Utils;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneralServicesTest {

    @Test
    public void checkAgeCalculationWhenCountLessThanLifeExpectancy() {
        Species wolf = Utils.createWolf();
        int count;
        List<Integer> expectedAges;
        List<Integer> calculatedAges;

        wolf.setLifeExpectancy(10);
        count = 3;
        expectedAges = Arrays.asList(3,6,9);
        calculatedAges = GeneralServices.calculateAgeSpread(wolf, count);
        assertEquals(expectedAges,calculatedAges);

        wolf.setLifeExpectancy(10);
        count = 9;
        expectedAges = Arrays.asList(1,2,3,4,5,6,7,8,9);
        calculatedAges = GeneralServices.calculateAgeSpread(wolf, count);
        assertEquals(expectedAges,calculatedAges);

        wolf.setLifeExpectancy(10);
        count = 10;
        expectedAges = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        calculatedAges = GeneralServices.calculateAgeSpread(wolf, count);
        assertEquals(expectedAges,calculatedAges);

    }

    @Test
    public void checkAgeCalculationWhenCountMoreThanLifeExpectancy() {
        Species wolf = Utils.createWolf();
        int count;
        List<Integer> expectedAges;
        List<Integer> calculatedAges;

        wolf.setLifeExpectancy(5);
        count = 6;
        expectedAges = Arrays.asList(0,1,2,3,4,5);
        calculatedAges = GeneralServices.calculateAgeSpread(wolf, count);
        assertEquals(expectedAges,calculatedAges);

        wolf.setLifeExpectancy(5);
        count = 10;
        expectedAges = Arrays.asList(0,1,2,3,4,5,0,1,2,3);
        calculatedAges = GeneralServices.calculateAgeSpread(wolf, count);
        assertEquals(expectedAges,calculatedAges);

        wolf.setLifeExpectancy(5);
        count = 13;
        expectedAges = Arrays.asList(0,1,2,3,4,5,0,1,2,3,4,5,0);
        calculatedAges = GeneralServices.calculateAgeSpread(wolf, count);
        assertEquals(expectedAges,calculatedAges);

    }


}