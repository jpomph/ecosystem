package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.config.Constants;
import com.pomphrey.ecosystem.model.configuration.InteractionJson;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.pomphrey.ecosystem.repository.InteractionRepository;
import com.pomphrey.ecosystem.model.configuration.Interaction;
import com.pomphrey.ecosystem.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InteractionController {

    @Autowired
    InteractionRepository interactionRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    Constants constants;

    @GetMapping("/interaction/query")
    public ResponseEntity queryInteraction(@RequestParam String species1, @RequestParam String species2){

        ResponseEntity responseEntity = null;
        Species consumer = speciesRepository.findByName(species1);
        Species consumed = speciesRepository.findByName(species2);
        Interaction interaction = interactionRepository.findByConsumerAndConsumed(consumer, consumed);

        if(interaction==null){
            responseEntity = new ResponseEntity<>(constants.INTERACTION_DOESNT_EXIST, HttpStatus.BAD_REQUEST);
        } else {
            InteractionJson responseBody = new InteractionJson(interaction);
            responseEntity = new ResponseEntity<InteractionJson>(responseBody, HttpStatus.OK);
        }
        return responseEntity;

    }

    @Transactional
    @GetMapping("/interaction/delete")
    public ResponseEntity deleteInteraction(@RequestParam String species1, @RequestParam String species2){

        Species consumer = speciesRepository.findByName(species1);
        Species consumed = speciesRepository.findByName(species2);
        interactionRepository.deleteByConsumerAndConsumed(consumer, consumed);
        return new ResponseEntity<String>("Interaction deleted", HttpStatus.OK);

    }

    @GetMapping("/interaction/list")
    public ResponseEntity getSpeciesDetails(@RequestParam String species1){

        ResponseEntity responseEntity = null;
        List<InteractionJson > interactions = interactionRepository.findAllBySpecies1(species1).stream().map(InteractionJson::new).collect(Collectors.toList());
        responseEntity = new ResponseEntity<List<InteractionJson>>(interactions, HttpStatus.OK);
        return responseEntity;

    }

    @PostMapping("/interaction/add")
    public ResponseEntity<String> addInteraction(@RequestBody InteractionJson interaction) {
        ResponseEntity responseEntity = null;
        String responseText = "Interaction added";
        Species consumer = speciesRepository.findByName(interaction.getConsumer());
        Species consumed = speciesRepository.findByName(interaction.getConsumed());
        if (consumer==null || consumed==null){
            responseEntity = new ResponseEntity<>(constants.SPECIES_DOESNT_EXIST, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        if (interactionRepository.findAllByConsumerAndConsumed(consumer, consumed).size() > 0) {
            responseEntity = new ResponseEntity<>(constants.INTERACTION_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        } else {
            try {
                interactionRepository.save(new Interaction(consumer, consumed, interaction.getInteractionType(), interaction.getAnnualAmount()));
            } catch (Exception ex) {
                responseText = ex.toString();
            }
            responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        }
        return responseEntity;
    }

    @PostMapping("/interaction/update")
    public ResponseEntity<String> updateInteraction(@RequestBody InteractionJson interaction) {
        ResponseEntity responseEntity = null;
        String responseText = "Interaction updated";
        Species consumer = speciesRepository.findByName(interaction.getConsumer());
        Species consumed = speciesRepository.findByName(interaction.getConsumed());
        if (interactionRepository.findAllByConsumerAndConsumed(consumer, consumed).size() == 0) {
            responseEntity = new ResponseEntity<>(constants.INTERACTION_DOESNT_EXIST, HttpStatus.BAD_REQUEST);
        } else {
            try {
                interactionRepository.save(new Interaction(consumer, consumed, interaction.getInteractionType(), interaction.getAnnualAmount()));
            } catch (Exception ex) {
                responseText = ex.toString();
            }
            responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        }
        return responseEntity;
    }

}
