package com.pomphrey.ecosystem.repository;

import com.pomphrey.ecosystem.model.configuration.Interaction;
import com.pomphrey.ecosystem.model.configuration.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Integer> {

    List<Interaction> findAllByConsumerAndConsumed(Species consumer, Species consumed);
    Interaction findByConsumerAndConsumed(Species consumer, Species consumed);
    void deleteByConsumerAndConsumed(Species consumer, Species consumed);

    @Query("select a from Interaction a where consumer.name = :species1")
    List<Interaction> findAllBySpecies1(String species1);

}
