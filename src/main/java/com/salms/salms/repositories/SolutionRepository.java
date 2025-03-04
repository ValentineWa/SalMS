package com.salms.salms.repositories;

import com.salms.salms.models.Solutions;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolutionRepository extends JpaRepository<Solutions, String> {


//    Optional<Solutions> findByServiceName(String serviceName);

    Solutions findByServiceName(String serviceName);

    //Delete a record by updating the fla
    @Modifying
    @Query("UPDATE Solutions s SET s.isDeleted = true WHERE s.id = :id")
    void deleteServiceByUpdatingId(@Param("id") String id);

    @Query("select s from Solutions s where s.isDeleted = false")
    List<Solutions> findByIsDeletedFalse();
}
