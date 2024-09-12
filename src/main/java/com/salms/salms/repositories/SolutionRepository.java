package com.salms.salms.repositories;

import com.salms.salms.models.Solutions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solutions, Long> {
}
