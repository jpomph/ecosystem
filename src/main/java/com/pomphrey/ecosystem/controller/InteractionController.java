package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.config.Constants;
import com.pomphrey.ecosystem.dao.InteractionRepository;
import com.pomphrey.ecosystem.dao.SpeciesRepository;
import com.pomphrey.ecosystem.exception.DataIntegrityException;
import com.pomphrey.ecosystem.model.Interaction;
import com.pomphrey.ecosystem.model.InteractionKey;
import com.pomphrey.ecosystem.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class InteractionController {

    @Autowired
    InteractionRepository interactionRepository;

    @Autowired
    Constants constants;

    @GetMapping("/interaction/query")
    public ResponseEntity queryInteraction(@RequestParam String species1, @RequestParam String species2){

        ResponseEntity responseEntity = null;
        InteractionKey interactionKey = new InteractionKey(species1, species2);
        Interaction interaction = interactionRepository.findByInteractionKey(interactionKey);

        if(interaction==null){
            responseEntity = new ResponseEntity<>(constants.INTERACTION_DOESNT_EXIST, HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<Interaction>(interaction, HttpStatus.OK);
        }
        return responseEntity;

    }

    @Transactional
    @GetMapping("/interaction/delete")
    public ResponseEntity deleteInteraction(@RequestParam String species1, @RequestParam String species2){

        InteractionKey interactionKey = new InteractionKey(species1, species2);
        interactionRepository.deleteByInteractionKey(interactionKey);
        return new ResponseEntity<String>("Interaction deleted", HttpStatus.OK);

    }

    @GetMapping("/interaction/list")
    public ResponseEntity getSpeciesDetails(@RequestParam String species1){

        ResponseEntity responseEntity = null;
        List<Interaction > interactions = interactionRepository.findAllBySpecies1(species1);responseEntity = new ResponseEntity<List<Interaction>>(interactions, HttpStatus.OK);
        return responseEntity;

    }

    @PostMapping("/interaction/add")
    public ResponseEntity<String> addInteraction(@RequestBody Interaction interaction) {
        ResponseEntity responseEntity = null;
        String responseText = "Interaction added";
        if (interactionRepository.findByInteractionKey(interaction.getInteractionKey()) != null) {
            responseEntity = new ResponseEntity<>(constants.INTERACTION_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        } else {
            try {
                interactionRepository.save(interaction);
            } catch (Exception ex) {
                responseText = ex.toString();
            }
            responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        }
        return responseEntity;
    }

    @PostMapping("/interaction/update")
    public ResponseEntity<String> updateInteraction(@RequestBody Interaction interaction) {
        ResponseEntity responseEntity = null;
        String responseText = "Interaction updated";
        if (interactionRepository.findByInteractionKey(interaction.getInteractionKey()) == null) {
            responseEntity = new ResponseEntity<>(constants.INTERACTION_DOESNT_EXIST, HttpStatus.BAD_REQUEST);
        } else {
            try {
                interactionRepository.save(interaction);
            } catch (Exception ex) {
                responseText = ex.toString();
            }
            responseEntity = new ResponseEntity<>(responseText, HttpStatus.OK);
        }
        return responseEntity;
    }

}
