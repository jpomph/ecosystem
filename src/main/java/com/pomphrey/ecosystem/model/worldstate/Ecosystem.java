package com.pomphrey.ecosystem.model.worldstate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Ecosystem {

    @Id
    @GeneratedValue
    long ecosystemId;

    LocalDate date;

    public Ecosystem(){
        date = LocalDate.of(2000,1,1);
    }

}
