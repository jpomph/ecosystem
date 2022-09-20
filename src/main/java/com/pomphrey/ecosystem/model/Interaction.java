package com.pomphrey.ecosystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Interaction {

    @EmbeddedId
    InteractionKey interactionKey;

    String interactionType;
    // p = predation
    // h = herbivory

    int annualAmount;

    public void setInteractionType(String interactionType) throws IllegalArgumentException{
        if (interactionType.equalsIgnoreCase("P") || interactionType.equalsIgnoreCase("H")){
            this.interactionType = interactionType;
        } else {
            throw new IllegalArgumentException("Invalid interaction type");
        }
        this.interactionType = interactionType;
    }

}
