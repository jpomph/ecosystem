package com.pomphrey.ecosystem.dao;

import com.pomphrey.ecosystem.model.configuration.InitialCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitialConditionRepository extends JpaRepository<InitialCondition, String> {

    InitialCondition findBySpecies(String species);

    void deleteBySpecies(String species);

    List<InitialCondition> findAll();

}
