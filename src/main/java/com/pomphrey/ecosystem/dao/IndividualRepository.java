package com.pomphrey.ecosystem.dao;

import com.pomphrey.ecosystem.model.worldstate.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {

    List<Individual> findAll();

}
