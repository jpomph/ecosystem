package com.pomphrey.ecosystem.model.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InteractionJson {

    String consumer;
    String consumed;
    String interactionType;
    int annualAmount;

    public InteractionJson(Interaction interaction){
        this.consumer = interaction.consumer.getName();
        this.consumed = interaction.consumed.getName();
        this.interactionType = interaction.getInteractionType();
        this.annualAmount = interaction.getAnnualAmount();
    }

}
