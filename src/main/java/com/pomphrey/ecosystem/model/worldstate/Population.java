package com.pomphrey.ecosystem.model.worldstate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pomphrey.ecosystem.model.configuration.Species;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Population {

    @Id
    @SequenceGenerator(sequenceName = "standard_sequence_generator", name = "standard_sequence_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "standard_sequence_generator")
    int populationId;

    @Column(name = "speciesID", nullable = false)
    String speciesName;

    @ManyToOne
    @JoinColumn(name = "ecosystemId", nullable = false)
    Ecosystem ecosystem;

    @JsonIgnore
    @OneToMany(mappedBy = "population", cascade = CascadeType.ALL)
    List<Individual> individuals;

    public Population(Species species, Ecosystem ecosystem) {
        this.speciesName = species.getName();
        this.ecosystem = ecosystem;
        this.individuals = new ArrayList<>();
    }

    public void addIndividual(Individual individual){
        individuals.add(individual);
    }

    public int getPopulationCount(){
        return individuals.size();
    }
}
