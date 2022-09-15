package com.pomphrey.ecosystem.utils;

import com.pomphrey.ecosystem.model.Species;

public class Utils {

    public static Species createWolf(){
        Species species = new Species();
        species.setName("wolf");
        species.setType("C");
        species.setFood("rabbit");
        species.setRequirement(50);
        species.setMature_age(2);
        species.setSenile_age(12);
        species.setInfant_survival(0.3);
        species.setSenile_survival(0.3);
        species.setGestation(1);
        species.setReproduction_period(2);

        return species;
    }
}
