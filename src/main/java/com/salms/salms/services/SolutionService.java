package com.salms.salms.services;

import com.salms.salms.dto.SolutionRequest;
import com.salms.salms.models.Solutions;
import com.salms.salms.repositories.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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
}
