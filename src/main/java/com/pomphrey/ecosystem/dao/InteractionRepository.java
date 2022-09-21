package com.pomphrey.ecosystem.dao;

import com.pomphrey.ecosystem.model.configuration.Interaction;
import com.pomphrey.ecosystem.model.configuration.InteractionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, InteractionKey> {

    Interaction findByInteractionKey(InteractionKey interactionKey);
    void deleteByInteractionKey(InteractionKey interactionKey);

    @Query("select a from Interaction a where a.interactionKey.species1 = :species1")
    List<Interaction> findAllBySpecies1(String species1);

}
