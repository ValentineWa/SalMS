package com.salms.salms.repositories;

import com.salms.salms.models.Solutions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolutionRepository extends JpaRepository<Solutions, Long> {


    Optional<Solutions> findByServiceName(String serviceName);

    Solutions findByServiceName2(String serviceName);
}
