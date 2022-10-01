package com.pomphrey.ecosystem.model.worldstate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ecosystem {

    @Id
    @GeneratedValue
    long ecosystemId;

    LocalDate date;

    @JsonIgnore
    @OneToMany(mappedBy = "ecosystem", cascade = CascadeType.ALL)
    List<Population> populations;

    public Ecosystem(){
        date = LocalDate.of(2000,1,1);
        populations = new ArrayList<>();
    }

    public void addPopulation(Population population){
        populations.add(population);
    }

    public int getAgeInYears(){
        return date.getYear() - 2000;
    }

}
