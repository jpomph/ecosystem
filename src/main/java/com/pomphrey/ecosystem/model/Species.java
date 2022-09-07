package com.pomphrey.ecosystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Species {

    private String name;
    private String type;
    //todo restrict entries
    private String food;
    private int requirement;
    private int maturity_age;
    private int senility_age;
    private double infant_survival;
    private double senile_survival;
    private int offspring;
    private int gestation;
    private int reproduction_period;

    public Species(String name, String type, String food, int requirement, int maturity_age, int senility_age, double infant_survival, double senile_survival, int offspring, int gestation, int reproduction_period) {
        this.name = name;
        this.type = type;
        this.food = food;
        this.requirement = requirement;
        this.maturity_age = maturity_age;
        this.senility_age = senility_age;
        this.infant_survival = infant_survival;
        this.senile_survival = senile_survival;
        this.offspring = offspring;
        this.gestation = gestation;
        this.reproduction_period = reproduction_period;
    }
}
