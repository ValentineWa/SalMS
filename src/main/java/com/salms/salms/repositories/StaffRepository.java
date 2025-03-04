package com.salms.salms.repositories;

import com.salms.salms.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByIdNumber(String idNumber);

    Staff findByStaffAlias(String staffAlias);
}
