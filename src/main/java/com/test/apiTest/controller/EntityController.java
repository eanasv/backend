package com.test.apiTest.controller;


import com.test.apiTest.dto.CategorySummaryWithScoreOfEntityDto;
import com.test.apiTest.dto.EntityPlainDto;
import com.test.apiTest.dto.EntityTotalSkillDto;
import com.test.apiTest.dto.EntityWithEmployeesDto;
import com.test.apiTest.model.Entities;
import com.test.apiTest.repository.EntityRepository;
import com.test.apiTest.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    private EntityService entityService;
    @Autowired
    private EntityRepository entityRepository;


    @GetMapping("/category-summary/{entityName}")
    public List<CategorySummaryWithScoreOfEntityDto> getCategorySummary(String entityName) {
        return entityService.categorySummaryWithScoreOfEntityDtoList(entityName);
    }

    @PostMapping("/addNew")
    public ResponseEntity<String> uploadEntity(@RequestParam("name") String label,
                                               @RequestParam("image") MultipartFile file) {
        String message = "";
        try {
            Entities entity = new Entities();
            entity.setLabel(label);
            byte[] imageBytes = file.getBytes();
            entity.setImage(imageBytes);
            entityRepository.save(entity);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!" + e;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @GetMapping("/entities-with-employees")
    public List<EntityWithEmployeesDto> getAllEntitiesWithEmployees() {
        List<Entities> entities = entityRepository.findAllWithEmployees();
        List<EntityWithEmployeesDto> dtos = new ArrayList<>();
        for (Entities entity : entities) {
            dtos.add(new EntityWithEmployeesDto(entity.getId(), entity.getLabel(), entity.getImage(), entity.getEmployees()));
        }
        return dtos;
    }

    @GetMapping("/getAll")
    public List<EntityTotalSkillDto> getEntitiesWithScorePercentage(@RequestParam("filter") String filter, @RequestParam("entityName") String entityName) {
        List<EntityTotalSkillDto> entityScoreDTOs = entityService.getEntityWthSkillPerce(entityName);

        if (filter.equals("asc")) {
            // Sort the entityScoreDTOs list by label in alphabetical order
            Comparator<EntityTotalSkillDto> alphabeticalOrderComparator = Comparator.comparing(EntityTotalSkillDto::getLabel);
            entityScoreDTOs.sort(alphabeticalOrderComparator);
        } else if (filter.equals("desc")) {
            //sort in reverse alphabetical order
            Comparator<EntityTotalSkillDto> reverseAlphabeticalOrderComparator = Comparator.comparing(EntityTotalSkillDto::getLabel).reversed();
            entityScoreDTOs.sort(reverseAlphabeticalOrderComparator);


        } else if (filter.equals("ascScore")) {

            // Sort the entityScoreDTOs list by totalSkillPercentage in ascending order
            Comparator<EntityTotalSkillDto> percentageOrderComparator = Comparator.comparing(EntityTotalSkillDto::getTotalSkillPercentage);
            entityScoreDTOs.sort(percentageOrderComparator);
        } else if (filter.equals("descScore")) {
            // Sort the entityScoreDTOs list by totalSkillPercentage in descending order
            Comparator<EntityTotalSkillDto> percentageOrderComparator = Comparator.comparing(EntityTotalSkillDto::getTotalSkillPercentage);
            entityScoreDTOs.sort(percentageOrderComparator.reversed());
        }

        return entityScoreDTOs;
    }


    @GetMapping("/getNameDetails")
    public List<EntityPlainDto> getAverageSkillOfEachEntity() {
        List<EntityPlainDto> detailsOfEachEntities = entityService.getEntityNameDetails();
        return detailsOfEachEntities;
    }

    @GetMapping("/{entityName}")
    public List<EntityTotalSkillDto> getIndividualEntity(@PathVariable String entityName) {
        List<EntityTotalSkillDto> entityScoreDTOs = entityService.getEntityWthSkillPerce(entityName);
        //List<EntityPlainDto> detailsOfEachEntities = entityService.getEntityNameDetails();
        return entityScoreDTOs;
    }

}
