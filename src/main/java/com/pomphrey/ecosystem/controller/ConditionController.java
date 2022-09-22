package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.config.Constants;
import com.pomphrey.ecosystem.repository.InitialConditionRepository;
import com.pomphrey.ecosystem.model.configuration.InitialCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class ConditionController {

    @Autowired
    InitialConditionRepository initialConditionRepository;

    @Autowired
    Constants constants;

    @GetMapping("/condition/query/{species}")
    public ResponseEntity getSingleCondition(@PathVariable("species") String species){
        InitialCondition condition = initialConditionRepository.findBySpecies(species);
        if(condition==null){
            return new ResponseEntity<>(constants.NO_CONDITION_FOUND, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(condition, HttpStatus.OK);
        }
    }

    @Transactional
    @GetMapping("/condition/delete/{species}")
    public ResponseEntity deleteSingleCondition(@PathVariable("species") String species){
        initialConditionRepository.deleteBySpecies(species);
        return new ResponseEntity<>(constants.CONDITION_DELETED, HttpStatus.OK);

    }

    @GetMapping("/condition/list")
    public ResponseEntity getAllConditions(){
        List<InitialCondition> conditions = initialConditionRepository.findAll();
        return new ResponseEntity<>(conditions, HttpStatus.OK);
    }

    @PostMapping("/condition/add")
    public ResponseEntity<String> addInteraction(@RequestBody InitialCondition condition) {
        String responseText = constants.CONDITION_ADDED;
        ResponseEntity responseEntity = null;
        if (initialConditionRepository.findBySpecies(condition.getSpecies()) != null) {
            responseEntity = new ResponseEntity<>(constants.CONDITION_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        } else {
            try {
                initialConditionRepository.save(condition);
            } catch (Exception ex) {
                responseText = ex.toString();
            }
            responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        }
        return responseEntity;
    }


}
