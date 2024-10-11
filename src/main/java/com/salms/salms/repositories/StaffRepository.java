package com.salms.salms.repositories;

import com.salms.salms.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByIdNumber(String idNumber);

    Staff findByAlias(String staffAlias);
}
