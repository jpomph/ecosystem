package com.pomphrey.ecosystem.model.worldstate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Individual {

    @Id
    @GeneratedValue
    long individualId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "populationId", nullable = false)
    Population population;

    String species;

    int age;

    public Individual(String species , int age, Population population){
        this.species = species;
        this.age = age;
        this.population = population;
    }

}
