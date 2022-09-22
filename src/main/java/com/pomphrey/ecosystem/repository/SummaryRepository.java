package com.pomphrey.ecosystem.repository;

import com.pomphrey.ecosystem.model.worldstate.Summary;
import com.pomphrey.ecosystem.model.worldstate.SummaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryRepository extends JpaRepository<Summary, SummaryKey> {



}
