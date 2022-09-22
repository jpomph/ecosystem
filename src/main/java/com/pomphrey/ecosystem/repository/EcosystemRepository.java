package com.pomphrey.ecosystem.repository;

import com.pomphrey.ecosystem.model.worldstate.Ecosystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EcosystemRepository extends JpaRepository<Ecosystem, LocalDate> {

    List<Ecosystem> findAll();

}
