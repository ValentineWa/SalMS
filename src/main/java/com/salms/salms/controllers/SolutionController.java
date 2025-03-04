package com.salms.salms.controllers;

import com.salms.salms.dto.ApiResponse;
import com.salms.salms.dto.CustomersResponse;
import com.salms.salms.dto.SolutionRequest;
import com.salms.salms.dto.SolutionResponse;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Customers;
import com.salms.salms.models.Solutions;
import com.salms.salms.repositories.SolutionRepository;
import com.salms.salms.services.SolutionService;
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
@RequestMapping("/api/v1/services")
@Slf4j
public class SolutionController {

    @Autowired
    private SolutionService solutionService;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;


    @PostMapping("/createNew")
    public ResponseEntity<ApiResponse<SolutionResponse>> createService(@RequestBody SolutionRequest solutionRequest) {
        String serviceName = solutionRequest.getServiceName();
            Solutions existingService = solutionRepository.findByServiceName(serviceName);


        Solutions newService = solutionService.createService(solutionRequest);
        SolutionResponse response = SolutionResponse.builder()
                .serviceName(newService.getServiceName())
                .duration(newService.getDuration())
                .price(newService.getPrice())
                .description(newService.getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Service created successfully", response));

    }

    @GetMapping("/getAllServices")
    public ResponseEntity<ApiResponse<List<SolutionResponse>>> getAllServices() {

        List<Solutions> allServices = solutionRepository.findByIsDeletedFalse();
        List<SolutionResponse> solutionResponse = allServices.stream()
                .map(service -> new SolutionResponse(
                        service.getId(),
                        service.getServiceName(),
                        service.getDuration(),
                        service.getPrice(),
                        service.getDescription()))
                .toList();

        ApiResponse<List<SolutionResponse>> response = new ApiResponse<>(200, "Solutions retrieved successfully", solutionResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<ApiResponse<SolutionResponse>> deleteService(@PathVariable String id){
        try {
            solutionService.deleteServiceById(id);
            ApiResponse<SolutionResponse> response = new ApiResponse<>(200, "Service Deleted Successfully",null);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Service Not Found", null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "An Error Occurred While Deleting The Service", null ));
        }
    }


    @PutMapping("/updateService/{id}")
    public ResponseEntity<Solutions> updateSolutions(@PathVariable String id, @RequestBody Solutions updatedSolution){

        Solutions solutions = solutionService.updateService(id, updatedSolution);
        return ResponseEntity.ok(solutions);

    }
}
