package com.pomphrey.ecosystem.model.worldstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Individual {

    @Id
    @GeneratedValue
    long id;

    String species;

    int age;

    public Individual(String species , int age){
        this.species = species;
        this.age = age;
    }

}
