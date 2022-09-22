package com.pomphrey.ecosystem.model.worldstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Summary {

    @EmbeddedId
    SummaryKey summaryKey;

    int individualCount;

}
