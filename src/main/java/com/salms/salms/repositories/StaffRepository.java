package com.salms.salms.repositories;

import com.salms.salms.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByIdNumber(String idNumber);

    Staff findByStaffAlias(String staffAlias);

    @Query("""
    SELECT DISTINCT s FROM Staff s
    LEFT JOIN FETCH s.staffSolutions ss
    LEFT JOIN FETCH ss.solution
""")
    List<Staff> findAllWithSolutions();

    @Query("""
    SELECT DISTINCT s FROM Staff s
    JOIN FETCH s.staffSolutions ss
    JOIN FETCH ss.solution
    WHERE s.staffAlias = :alias
""")
    Optional<Staff> findByStaffAliasWithSolutions(@Param("alias") String alias);
}
