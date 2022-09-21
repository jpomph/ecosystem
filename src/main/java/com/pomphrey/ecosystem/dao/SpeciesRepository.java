package com.pomphrey.ecosystem.dao;


import com.pomphrey.ecosystem.model.configuration.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, String> {

    Species findByName(String name);

    void deleteByName(String name);

}
