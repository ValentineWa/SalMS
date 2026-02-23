package com.salms.salms.services;

import com.salms.salms.dto.StaffRequest;
import com.salms.salms.models.Solution;
import com.salms.salms.models.StaffSolution;
import com.salms.salms.repositories.SolutionRepository;
import com.salms.salms.repositories.StaffRepository;
import com.salms.salms.repositories.StaffSolutionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@Transactional
public class StaffService {

    private final StaffRepository staffRepository;
    private final SolutionRepository solutionRepository;
    private final StaffSolutionRepository staffSolutionRepository;

    public StaffService(
            StaffRepository staffRepository,
            SolutionRepository solutionRepository,
            StaffSolutionRepository staffSolutionRepository) {

        this.staffRepository = staffRepository;
        this.solutionRepository = solutionRepository;
        this.staffSolutionRepository = staffSolutionRepository;
    }

    public com.salms.salms.models.Staff createStaff(StaffRequest request) {

        // 1️⃣ Create Staff
        com.salms.salms.models.Staff staff = new com.salms.salms.models.Staff();
        staff.setStaffName(request.getStaffName());
        staff.setStaffAlias(request.getStaffAlias());
        staff.setIdNumber(request.getIdNumber());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setStartDate(request.getStartDate());
        staff.setYearsOfExperience(request.getYearsOfExperience());
        staff.setNationality(request.getNationality());
        staff.setPhysicalAddress(request.getPhysicalAddress());
        staff.setCreationDate(Instant.now());

        staffRepository.save(staff);

        // 2️⃣ Link services
        for (String serviceId : request.getServiceIds()) {

            Solution solution = solutionRepository.findById(serviceId)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Service not found: " + serviceId));

            StaffSolution staffSolution = new StaffSolution();
            staffSolution.setStaff(staff);
            staffSolution.setSolution(solution);
            staffSolution.setActive(true);

            staffSolutionRepository.save(staffSolution);
        }

        log.info("Staff [{}] created successfully", staff.getStaffName());

        return staff;
    }

    public void deleteStaffById (UUID id){

        if (staffRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Customer With ID " + id + "Not Found" );
        }
        staffRepository.deleteById(id);
    }

    public com.salms.salms.models.Staff updateStaff (UUID id, com.salms.salms.models.Staff updatedStaff) {

        com.salms.salms.models.Staff existingStaff = staffRepository.findById(id)
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