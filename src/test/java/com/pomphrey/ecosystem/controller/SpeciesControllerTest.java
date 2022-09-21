package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.dao.SpeciesRepository;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.service.CarnivoreServices;
import com.pomphrey.ecosystem.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpeciesControllerTest {

    Species wolf;

    @InjectMocks
    SpeciesController speciesController;

    @Mock
    SpeciesRepository mockSpeciesRepository;

    @Mock
    CarnivoreServices MockCarnivoreServices;

    @BeforeEach
    public void setUp(){
        wolf = Utils.createWolf();
    }

    @Test
    void testValidQueryReturnsValidObject(){
        String speciesName = "wolf";
        when(mockSpeciesRepository.findByName(speciesName)).thenReturn(wolf);
        ResponseEntity responseEntity = speciesController.getSpeciesDetails(speciesName);
        assertEquals(wolf, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testQueryNotFoundReturnsBadRequest(){
        String speciesName = "dragon";
        when(mockSpeciesRepository.findByName(speciesName)).thenThrow(new IllegalArgumentException("Species Not Found"));
        ResponseEntity responseEntity = speciesController.getSpeciesDetails(speciesName);
        assertEquals("Species Not Found", responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUnhandledDbError(){
        String speciesName = "dragon";
        when(mockSpeciesRepository.findByName(speciesName)).thenThrow(new IllegalArgumentException("Database Unavailable"));
        ResponseEntity responseEntity = speciesController.getSpeciesDetails(speciesName);
        assertEquals("Unhandled error", responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testValidInsert(){
//        doNothing().when(mockSpeciesRepository).save(wolf);
        ResponseEntity responseEntity = speciesController.addSpecies(wolf);
    }

    @Test
    void testInvalidInsert(){
        doThrow(new IllegalArgumentException("")).when(mockSpeciesRepository).save(wolf);
        ResponseEntity responseEntity = speciesController.addSpecies(wolf);
        assertEquals("Unhandled error", responseEntity.getBody());

    }

}