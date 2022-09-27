package com.pomphrey.ecosystem.utils;

import com.pomphrey.ecosystem.model.configuration.InitialCondition;
import com.pomphrey.ecosystem.model.configuration.Interaction;
import com.pomphrey.ecosystem.model.configuration.Species;

public class Utils {

    public static Species createWolf(){
        Species species = new Species();
        species.setName("wolf");
        species.setType("C");
        species.setLifeExpectancy(14);
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
        species.setLifeExpectancy(14);
        species.setMature_age(2);
        species.setSenile_age(12);
        species.setInfant_survival(0.3);
        species.setSenile_survival(0.3);
        species.setGestation(1);
        species.setReproduction_period(2);

        return species;
    }

    public static Species createGrain(){
        Species species = new Species();
        species.setName("grain");
        species.setType("P");
        species.setLifeExpectancy(999);
        species.setMature_age(0);
        species.setSenile_age(999);
        species.setInfant_survival(1.0);
        species.setSenile_survival(1.0);
        species.setGestation(0);
        species.setReproduction_period(999);

        return species;
    }
   public static Species createGrass(){
        Species species = new Species();
        species.setName("grass");
        species.setType("P");
        species.setLifeExpectancy(999);
        species.setMature_age(0);
        species.setSenile_age(999);
        species.setInfant_survival(1.0);
        species.setSenile_survival(1.0);
        species.setGestation(0);
        species.setReproduction_period(999);

        return species;
    }

    public static Interaction createChickenGrainInteraction(){
        Interaction interaction = new Interaction();
        interaction.setConsumer(Utils.createChickeen());
        interaction.setConsumed(Utils.createGrain());
        interaction.setInteractionType("h");
        interaction.setAnnualAmount(1);

        return interaction;
    }

    public static Interaction createChickenGrassInteraction(){
        Interaction interaction = new Interaction();
        interaction.setConsumer(createChickeen());
        interaction.setConsumed(createGrass());
        interaction.setInteractionType("h");
        interaction.setAnnualAmount(1);

        return interaction;
    }

    public static InitialCondition createChickenCondition(){
        return new InitialCondition("chicken", 10);
    }

    public static InitialCondition createTurkeyCondition(){
        return new InitialCondition("turkey", 10);
    }
}
