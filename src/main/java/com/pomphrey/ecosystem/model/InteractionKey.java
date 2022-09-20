package com.pomphrey.ecosystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class InteractionKey implements Serializable {

    private String species1;
    private String species2;

    public InteractionKey(String species1, String species2){
        this.species1 = species1;
        this.species2 = species2;
    }

}
