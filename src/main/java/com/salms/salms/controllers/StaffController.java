package com.salms.salms.controllers;

import com.salms.salms.dto.ApiResponse;
import com.salms.salms.dto.CustomersResponse;
import com.salms.salms.dto.StaffRequest;
import com.salms.salms.dto.StaffResponse;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Customers;
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
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @PostMapping("/createNew")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(@RequestBody StaffRequest staffRequest) {
        String idNumber = staffRequest.getIdNumber();
        Optional<Staff> existingStaff = staffRepository.findByIdNumber(idNumber);

        Staff newStaff = staffService.createStaff(staffRequest);

        StaffResponse response = StaffResponse.builder()
                .staffName(newStaff.getStaffName())
                .staffAlias(newStaff.getStaffAlias())
                .idNumber(newStaff.getIdNumber())
                .phoneNumber(newStaff.getPhoneNumber())
                .startDate(newStaff.getStartDate())
                .yearOfExperience(newStaff.getYearsOfExperience())
                .nationality(newStaff.getNationality())
                .physicalAddress(newStaff.getPhysicalAddress())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Customers created successfully", response));
    }



    @GetMapping("/getAllStaff")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getAllStaff() {
        List<Staff> allStaff = staffRepository.findAll();

        List<StaffResponse> staffResponses = allStaff.stream()
                .map(staff -> new StaffResponse(
                        staff.getId(),
                        staff.getStaffName(),
                        staff.getStaffAlias(),
                        staff.getIdNumber(),
                        staff.getPhoneNumber(),
                        staff.getStartDate(),
                        staff.getYearsOfExperience(),
                        staff.getNationality(),
                        staff.getPhysicalAddress(),
                        staff.getSolutions()))
                .toList();

        ApiResponse<List<StaffResponse>> response = new ApiResponse<>(200, "Staff retrieved successfully", staffResponses);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteStaff/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> deleteStaff(@PathVariable UUID id){
        try {
            staffService.deleteStaffById(id);
            ApiResponse<StaffResponse> response = new ApiResponse<>(200, "Staff Deleted Successfully",null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Staff Not Found", null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "An Error Occurred While Deleting The Customer", null ));
        }
    }


    @PutMapping("/updateStaff/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable UUID id, @RequestBody Staff updatedStaff){

        Staff staff = staffService.updateStaff(id, updatedStaff);
        return ResponseEntity.ok(staff);

    }



}