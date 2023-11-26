package com.test.apiTest.controller;

import com.test.apiTest.service.TrainingNeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TrainingNeedController {

    @Autowired
    TrainingNeedService trainingNeedService;
//    @GetMapping("/trainingNeeded")
//    public ResponseEntity<List<TrainingNeedDTO>> getTrainingNeedsByEmployeeNumber(@RequestParam Long employeeNumber) {
//        List<TrainingNeedDTO> trainingNeeds = null;
//        try {
//            trainingNeeds = trainingNeedService.getTrainingNeedByEmployeeNumber(employeeNumber);
//        } catch (ChangeSetPersister.NotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return ResponseEntity.ok(trainingNeeds);
//    }
}
