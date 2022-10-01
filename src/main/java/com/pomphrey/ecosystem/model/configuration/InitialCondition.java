package com.pomphrey.ecosystem.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InitialCondition {

    @Id
    String species;

    int individualCount;

}
