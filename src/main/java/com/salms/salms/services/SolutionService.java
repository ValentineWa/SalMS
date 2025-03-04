package com.salms.salms.services;

import com.salms.salms.dto.SolutionRequest;
import com.salms.salms.models.Customers;
import com.salms.salms.models.Solutions;
import com.salms.salms.repositories.SolutionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    public Solutions createService (SolutionRequest solutionRequest){
        Solutions solution = new Solutions();

        int random4Digit = new Random().nextInt(9000) + 1000;

        String uniqueId = random4Digit +"-" + solutionRequest.getServiceName();
        solution.setId(uniqueId);
        solution.setServiceName(solutionRequest.getServiceName());
        solution.setDuration(solutionRequest.getDuration());
        solution.setPrice(solutionRequest.getPrice());
        solution.setDescription(solutionRequest.getDescription());
        solutionRepository.save(solution);

        log.info("SERVICE [%s] HAS BEEN CREATED SUCCESSFULLY", solutionRequest.getServiceName() );

        return solution;
    }

    @Transactional
    public void deleteServiceById (String id){

        if (solutionRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Customer With ID " + id + "Not Found" );
        }
        solutionRepository.deleteServiceByUpdatingId(id);
    }

        public Solutions updateService (String id, Solutions updatedSolution){

        Solutions existingSolution = solutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solutions with ID " + id + " not found"));
        existingSolution.setServiceName(updatedSolution.getServiceName());
        existingSolution.setDuration(updatedSolution.getDuration());
        existingSolution.setPrice(updatedSolution.getPrice());
        existingSolution.setDescription(updatedSolution.getDescription());
        return solutionRepository.save(existingSolution);
    }

}
