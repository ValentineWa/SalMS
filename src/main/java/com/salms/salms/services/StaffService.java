package com.salms.salms.services;

import com.salms.salms.dto.StaffRequest;
import com.salms.salms.models.Customers;
import com.salms.salms.models.Solutions;
import com.salms.salms.models.Staff;
import com.salms.salms.repositories.SolutionRepository;
import com.salms.salms.repositories.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private SolutionRepository solutionRepository;



    public Staff createStaff (StaffRequest staffRequest){

        //5. Get the primary service getting done
        List<Solutions> services = new ArrayList<>();
          for (String serviceName : staffRequest.getServiceNames()) {

            Solutions sol = solutionRepository.findByServiceName(serviceName);
            if (sol == null) {
                log.warn("SERVICE SELECTED NOT FOUND: {}", serviceName);
                continue;
            }
            services.add(sol);

        }
          try {
              Staff staff = new Staff();
              staff.setId(UUID.randomUUID());
              staff.setStaffName(staffRequest.getStaffName());
              staff.setStaffAlias(staffRequest.getStaffAlias());
              staff.setIdNumber(staffRequest.getIdNumber());
              staff.setPhoneNumber(staffRequest.getPhoneNumber());
              staff.setStartDate(staffRequest.getStartDate());
              staff.setYearsOfExperience(staffRequest.getYearsOfExperience());
              staff.setNationality(staffRequest.getNationality());
              staff.setPhysicalAddress(staffRequest.getPhysicalAddress());
              staff.setCreationDate(Instant.now());
              staff.setSolutions(services);

              staffRepository.save(staff);

              log.info("STAFF [%s] HAS BEEN CREATED SUCCESSFULLY", staffRequest.getStaffName());
              return staff;
          } catch(Exception e){
              log.error("Record not saved. Unexpected Error occurred: {}", e.getMessage());
              throw new IllegalArgumentException("An unexpected error occurred", e);

          }


    }

    public void deleteStaffById (UUID id){

        if (staffRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Customer With ID " + id + "Not Found" );
        }
        staffRepository.deleteById(id);
    }

    public Staff updateStaff (UUID id, Staff updatedStaff) {

        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
        existingStaff.setStaffName(updatedStaff.getStaffName());
        existingStaff.setStaffAlias(updatedStaff.getStaffAlias());
        existingStaff.setIdNumber(updatedStaff.getIdNumber());
        existingStaff.setPhoneNumber(updatedStaff.getPhoneNumber());
        existingStaff.setStartDate(updatedStaff.getStartDate());
        existingStaff.setYearsOfExperience(updatedStaff.getYearsOfExperience());
        existingStaff.setNationality(updatedStaff.getNationality());
        existingStaff.setPhysicalAddress(updatedStaff.getPhysicalAddress());
        existingStaff.setSolutions(updatedStaff.getSolutions());
        return staffRepository.save(existingStaff);
    }

}