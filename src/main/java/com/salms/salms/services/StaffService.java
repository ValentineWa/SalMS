package com.salms.salms.services;

import com.salms.salms.dto.CustomersRequest;
import com.salms.salms.dto.StaffRequest;
import com.salms.salms.models.Customers;
import com.salms.salms.models.Staff;
import com.salms.salms.repositories.CustomerRepository;
import com.salms.salms.repositories.StaffRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public Staff createStaff (StaffRequest staffRequest){
            Staff staff = new Staff();
        staff.setId(UUID.randomUUID());
        staff.setStaffName(staffRequest.getStaffName());
        staff.setIdNumber(staffRequest.getIdNumber());
        staff.setPhoneNumber(staffRequest.getPhoneNumber());
        staff.setStartDate(staffRequest.getStartDate());
        staff.setYearsOfExperience(staffRequest.getYearsOfExperience());
        staff.setNationality(staffRequest.getNationality());
        staff.setPhysicalAddress(staffRequest.getPhysicalAddress());
        staff.setCreationDate(Instant.now());
        staffRepository.save(staff);

        log.info("STAFF [%s] HAS BEEN CREATED SUCCESSFULLY", staffRequest.getStaffName());

        return staff;
    }

}
