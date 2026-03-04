package com.salms.salms.services;

import com.salms.salms.dto.StaffRequest;
import com.salms.salms.models.Solution;
import com.salms.salms.models.Staff;
import com.salms.salms.models.StaffSolution;
import com.salms.salms.repositories.SolutionRepository;
import com.salms.salms.repositories.StaffRepository;
import com.salms.salms.repositories.StaffSolutionRepository;
import jakarta.persistence.Entity;
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

        Staff savedStaff = staffRepository.save(staff);

        // 2️⃣ Link services
        for (String serviceId : request.getServiceIds()) {

            Solution solution = solutionRepository.findById(serviceId)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Service not found: " + serviceId));

            StaffSolution staffSolution = new StaffSolution();
            staffSolution.setStaff(savedStaff);
            staffSolution.setSolution(solution);
            staffSolution.setCustomPrice(solution.getPrice());
            staffSolution.setSkillLevel(String.valueOf(staff.getYearsOfExperience()));
            staffSolution.setActive(true);
            staffSolutionRepository.save(staffSolution);
        }

        log.info("Staff [{}] created successfully", staff.getStaffName());

        return savedStaff;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAllWithSolutions();
    }

    public void deleteStaffById(UUID id) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found"));

        staffRepository.delete(staff);
    }

    public Staff updateStaff (UUID id, StaffRequest request) {

        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
            //1. Update basic fields
        existingStaff.setStaffName(request.getStaffName());
        existingStaff.setStaffAlias(request.getStaffAlias());
        existingStaff.setIdNumber(request.getIdNumber());
        existingStaff.setPhoneNumber(request.getPhoneNumber());
        existingStaff.setStartDate(request.getStartDate());
        existingStaff.setYearsOfExperience(request.getYearsOfExperience());
        existingStaff.setNationality(request.getNationality());
        existingStaff.setPhysicalAddress(request.getPhysicalAddress());

        //2. Remove old relationships
        staffSolutionRepository.deleteByStaff(existingStaff);

        //3.  Add new relationships
        for (String serviceId : request.getServiceIds()) {

            Solution solution = solutionRepository.findById(serviceId)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Service not found"));

            StaffSolution staffSolution = new StaffSolution();
            staffSolution.setStaff(existingStaff);
            staffSolution.setSolution(solution);
            staffSolution.setActive(true);

            staffSolutionRepository.save(staffSolution);
            existingStaff.getStaffSolutions().add(staffSolution);
        }

        return staffRepository.save(existingStaff);
    }

}