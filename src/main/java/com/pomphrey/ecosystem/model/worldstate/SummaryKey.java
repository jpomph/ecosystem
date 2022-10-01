package com.pomphrey.ecosystem.model.worldstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryKey implements Serializable {

    LocalDate date;

    String species;

    public int getYear(){
        return date.getYear();
    }

}
