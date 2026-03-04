package com.salms.salms.repositories;

import com.salms.salms.models.Staff;
import com.salms.salms.models.StaffSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffSolutionRepository extends JpaRepository<StaffSolution, UUID> {

    void deleteByStaff(Staff staff);

    @Query("""
    SELECT DISTINCT s FROM Staff s
    LEFT JOIN FETCH s.staffSolutions ss
    LEFT JOIN FETCH ss.solution """)
    List<Staff> findAllWithSolutions();


}
