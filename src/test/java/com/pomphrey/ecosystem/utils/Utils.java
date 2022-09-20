package com.pomphrey.ecosystem.utils;

import com.pomphrey.ecosystem.model.Interaction;
import com.pomphrey.ecosystem.model.InteractionKey;
import com.pomphrey.ecosystem.model.Species;

import javax.persistence.criteria.CriteriaBuilder;

public class Utils {

    public static Species createWolf(){
        Species species = new Species();
        species.setName("wolf");
        species.setType("C");
        species.setMature_age(2);
        species.setSenile_age(12);
        species.setInfant_survival(0.3);
        species.setSenile_survival(0.3);
        species.setGestation(1);
        species.setReproduction_period(2);

        return species;
    }

    public static Species createChickeen(){
        Species species = new Species();
        species.setName("chicken");
        species.setType("C");
        species.setMature_age(2);
        species.setSenile_age(12);
        species.setInfant_survival(0.3);
        species.setSenile_survival(0.3);
        species.setGestation(1);
        species.setReproduction_period(2);

        return species;
    }

    public static Interaction createChickenGrainInteraction(){
        Interaction interaction = new Interaction();
        interaction.setInteractionKey(new InteractionKey("chicken","grain"));
        interaction.setInteractionType("h");
        interaction.setAnnualAmount(1);

        return interaction;
    }

    public static Interaction createChickenGrassInteraction(){
        Interaction interaction = new Interaction();
        interaction.setInteractionKey(new InteractionKey("chicken","grass"));
        interaction.setInteractionType("h");
        interaction.setAnnualAmount(1);

        return interaction;
    }
}
