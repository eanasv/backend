package com.test.apiTest.service;

import com.test.apiTest.repository.TrainingNeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingNeedService {

    @Autowired
    TrainingNeedRepository trainingNeedRepository;
//    public List<TrainingNeedDTO> getTrainingNeedByEmployeeNumber(Long employeeNumber) throws ChangeSetPersister.NotFoundException {
//        List<TrainingNeed> trainingNeeds = trainingNeedRepository.findAllByEmployee_EmployeeNumber(employeeNumber);
//
//        return trainingNeeds.stream()
//                .map(trainingNeed -> new TrainingNeedDTO(trainingNeed.getCourse(), trainingNeed.getEnrollmentDate(), trainingNeed.getEnrollmentStatus(), trainingNeed.getCourse(), trainingNeed.getLinkedCompetency()))
//                .collect(Collectors.toList());
//    }
}
