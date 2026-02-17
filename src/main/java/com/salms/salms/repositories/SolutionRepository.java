package com.salms.salms.repositories;

import com.salms.salms.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, String> {


//    Optional<Solutions> findByServiceName(String serviceName);

    Solution findByServiceName(String serviceName);

    //Delete a record by updating the fla
    @Modifying
    @Query("UPDATE Solution s SET s.isDeleted = true WHERE s.id = :id")
    void deleteServiceByUpdatingId(@Param("id") String id);

    @Query("select s from Solution s where s.isDeleted = false")
    List<Solution> findByIsDeletedFalse();
}
