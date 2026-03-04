package com.salms.salms.controllers;

import com.salms.salms.dto.ApiResponse;
import com.salms.salms.dto.StaffRequest;
import com.salms.salms.dto.StaffResponse;
import com.salms.salms.dto.StaffSolutionSummary;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Staff;
import com.salms.salms.repositories.StaffRepository;
import com.salms.salms.services.StaffService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff")
@Slf4j
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;
//    @Autowired
//    private GlobalExceptionHandler globalExceptionHandler;

    @PostMapping("/createNew")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(@RequestBody StaffRequest staffRequest) {
        String idNumber = staffRequest.getIdNumber();
        Optional<Staff> existingStaff = staffRepository.findByIdNumber(idNumber);
        if (existingStaff.isPresent()) {
            throw new IllegalArgumentException("Staff with this ID number already exists");
        }
        Staff newStaff = staffService.createStaff(staffRequest);
        List<StaffSolutionSummary> services = newStaff.getStaffSolutions()
                .stream()
                .map(sss -> StaffSolutionSummary.builder()
                        .id(UUID.fromString(sss.getSolution().getId()))
                        .name(sss.getSolution().getServiceName()) // adjust field name
                        .active(sss.isActive())
                        .customPrice(sss.getCustomPrice())
                        .skillLevel(sss.getSkillLevel())
                        .build())
                .toList();
        StaffResponse response = StaffResponse.builder()
                .staffName(newStaff.getStaffName())
                .staffAlias(newStaff.getStaffAlias())
                .idNumber(newStaff.getIdNumber())
                .phoneNumber(newStaff.getPhoneNumber())
                .startDate(newStaff.getStartDate())
                .yearOfExperience(newStaff.getYearsOfExperience())
                .nationality(newStaff.getNationality())
                .physicalAddress(newStaff.getPhysicalAddress())
                .services(services)


                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Customers created successfully", response));
    }

    @GetMapping("/getAllStaff")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getAllStaff() {

        List<Staff> allStaff = staffRepository.findAllWithSolutions();

        List<StaffResponse> staffResponses = allStaff.stream()
                .map(staff -> StaffResponse.builder()
                        .id(staff.getId())
                        .staffName(staff.getStaffName())
                        .staffAlias(staff.getStaffAlias())
                        .idNumber(staff.getIdNumber())
                        .phoneNumber(staff.getPhoneNumber())
                        .startDate(staff.getStartDate())
                        .yearOfExperience(staff.getYearsOfExperience())
                        .nationality(staff.getNationality())
                        .physicalAddress(staff.getPhysicalAddress())
                        .services(staff.getStaffSolutions().stream()
                                        .map(ss -> StaffSolutionSummary.builder()
                                                .name(ss.getSolution().getServiceName())
                                                .build())
                                        .toList()
                        )
                        .build())
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Staff retrieved successfully", staffResponses)
        );
    }

    @DeleteMapping("/deleteStaff/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> deleteStaff(@PathVariable UUID id){
        try {
            staffService.deleteStaffById(id);
            ApiResponse<StaffResponse> response = new ApiResponse<>(200, "StaffService Deleted Successfully",null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "StaffService Not Found", null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "An Error Occurred While Deleting The Customer", null ));
        }
    }

    @PutMapping("/updateStaff/{id}")
    public ResponseEntity<Staff> updateStaff(
            @PathVariable UUID id,
            @RequestBody StaffRequest request) {

        Staff staff = staffService.updateStaff(id, request);
        return ResponseEntity.ok(staff);
    }

}