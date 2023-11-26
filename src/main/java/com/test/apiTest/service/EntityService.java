package com.test.apiTest.service;

import com.test.apiTest.dto.*;
import com.test.apiTest.model.Employee;
import com.test.apiTest.model.Entities;
import com.test.apiTest.model.Skill;
import com.test.apiTest.repository.EmployeeRepository;
import com.test.apiTest.repository.EntityRepository;
import com.test.apiTest.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class EntityService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    EntityRepository entityRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CategorySummaryWithScoreOfEntityDto> categorySummaryWithScoreOfEntityDtoList(String entityName) {
        String sql = "SELECT e.category, COUNT( s.name) AS num_skills, SUM(s.score) AS total_score FROM employee e INNER JOIN skill s ON e.employee_number = s.employee_number WHERE e.entity = ? GROUP BY e.category";

        List<CategorySummaryWithScoreOfEntityDto> result = jdbcTemplate.query(sql, new Object[]{entityName}, rs -> {
            List<CategorySummaryWithScoreOfEntityDto> categorySummaryList = new ArrayList<>();
            while (rs.next()) {
                CategorySummaryWithScoreOfEntityDto categorySummary = new CategorySummaryWithScoreOfEntityDto();
                categorySummary.setCategory(rs.getString("category"));
                categorySummary.setNumSkills(rs.getInt("num_skills"));
                categorySummary.setTotalScore(rs.getInt("total_score"));
                categorySummaryList.add(categorySummary);
            }
            return categorySummaryList;
        });

        return result;
    }

    public List<EmployeeDto> getAllEmployeesInAnEntity(String entityName) {
        List<Employee> employees = employeeRepository.findByEntity(entityName);
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        //List<CategorySummaryWithScoreOfEntityDto> categorySummaryWithScoreOfEntityDtos = new ArrayList<>();
        for (Employee employee : employees) {
            CategorySummaryWithScoreOfEntityDto categorySummaryWithScoreOfEntityDto = new CategorySummaryWithScoreOfEntityDto();
            categorySummaryWithScoreOfEntityDto.setCategory(employee.getCategory());

            List<Skill> skills = skillRepository.findByEmployeeNumber(employee.getEmployeeNumber());
            List<SkillDto> skillDtos = new ArrayList<>();
            Integer score = 0;
            Integer totalSkills = 0;
            for (Skill skill : skills) {
                SkillDto skillDto = new SkillDto();
                skillDto.setName(skill.getName());
                //score =skill.getScore()+score;
                totalSkills = totalSkills + 1;
                skillDtos.add(skillDto);
            }
            categorySummaryWithScoreOfEntityDto.setTotalScore(score);
            categorySummaryWithScoreOfEntityDto.setNumSkills(totalSkills);
            //employeeDto.setSkills(skillDtos);
            //categorySummaryWithScoreOfEntityDtos.add(categorySummaryWithScoreOfEntityDto);
        }
        return employeeDtos;
    }

//    public List<Entities> getAllEntities() {
//        List<Entities> entities = entityRepository.findAllEntities();
//        return entities;
//    }

    public List<EntityTotalSkillDto> getEntityWthSkillPerce(String entityName) {

        List<Entities> entities;
        if (entityName != null && !entityName.isEmpty()) {
            // Fetch details for the specified entity
            entities = Arrays.asList(entityRepository.findByLabel(entityName));
        } else {
            // Fetch details for all entities
            entities = entityRepository.findAll();
        }


//        List<Entities> entities = entityRepository.findAll();
        List<EntityTotalSkillDto> entityScoreDTOs = new ArrayList<>();

        for (Entities entity : entities) {
            EntityTotalSkillDto scoreDto = new EntityTotalSkillDto();
            scoreDto.setLabel(entity.getLabel());
            scoreDto.setImage(entity.getImage());
            scoreDto.setValue(entity.getLabel());
            List<Employee> employees = employeeRepository.findByEntity(entity.getLabel());


            int totalSkills = 0;
            int totalAchievedSkills = 0;
            for (Employee employee : employees) {
                List<Skill> skills = skillRepository.findByEmployeeNumber(employee.getEmployeeNumber());
                // scoreDto.setMaxDate(skills[]);
                for (Skill skill : skills) {
                    totalSkills++;
                    if ("achieved".equalsIgnoreCase(skill.getEvaluationResult())) {
                        totalAchievedSkills++;
                    }
                }
            }
            if (totalSkills > 0) {
                double scorePercentage = ((double) totalAchievedSkills / (double) totalSkills) * 100;
                scoreDto.setTotalSkillPercentage(scorePercentage);
            }
            entityScoreDTOs.add(scoreDto);
        }

        return entityScoreDTOs;
    }


    public List<EntityPlainDto> getEntityNameDetails() {
        List<Entities> entities = entityRepository.findAll();
        List<EntityPlainDto> entityPlainDtos = new ArrayList<>();

        for (Entities entity : entities) {
            EntityPlainDto entityPlainDto = new EntityPlainDto();
            entityPlainDto.setLabel(entity.getLabel().toLowerCase());
            entityPlainDto.setValue(entity.getLabel().toLowerCase());
            entityPlainDtos.add(entityPlainDto);
        }

        // Sort the entityPlainDtos list by label in alphabetical order
        Comparator<EntityPlainDto> labelComparator = Comparator.comparing(EntityPlainDto::getLabel);
        entityPlainDtos.sort(labelComparator);

        return entityPlainDtos;
    }
}
