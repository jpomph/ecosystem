package com.pomphrey.ecosystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Species {

    private String name;
    private String type;
    private int mature_age;
    private int senile_age;
    private double infant_survival;
    private double senile_survival;
    private int offspring;
    private int gestation;
    private int reproduction_period;

    public void setType(String type) {
        if(type.equalsIgnoreCase("C") || type.equalsIgnoreCase("H")){
            this.type = type;
        }
        else {
            throw new IllegalArgumentException("Invalid Species Type");
        }
    }

    public Species(String name, String type, int mature_age, int senile_age, double infant_survival, double senile_survival, int offspring, int gestation, int reproduction_period) {
        this.name = name;
        this.type = type;
        this.mature_age = mature_age;
        this.senile_age = senile_age;
        this.infant_survival = infant_survival;
        this.senile_survival = senile_survival;
        this.offspring = offspring;
        this.gestation = gestation;
        this.reproduction_period = reproduction_period;
    }

    public Species() {
    }
}
