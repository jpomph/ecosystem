package com.pomphrey.ecosystem.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Interaction {

    @Id
    @GeneratedValue
    int interactionId;

    @ManyToOne
    @JoinColumn(name = "consumer")
    Species consumer;

    @ManyToOne
    @JoinColumn(name = "consumed")
    Species consumed;

    String interactionType;
    // p = predation
    // h = herbivory

    int annualAmount;

    public Interaction(Species consumer, Species consumed, String interactionType, int annualAmount){
        this.consumer = consumer;
        this.consumed = consumed;
        setInteractionType(interactionType);
        this.annualAmount = annualAmount;
    }

    public void setInteractionType(String interactionType) throws IllegalArgumentException{
        if (interactionType.equalsIgnoreCase("P") || interactionType.equalsIgnoreCase("H")){
            this.interactionType = interactionType;
        } else {
            throw new IllegalArgumentException("Invalid interaction type");
        }
        this.interactionType = interactionType;
    }

    public String getInteractionTypeFormatted(){
        if(interactionType.equalsIgnoreCase("C")){
            return "Carnivory";
        } else {
            return "Herbivory";
        }
    }

}
