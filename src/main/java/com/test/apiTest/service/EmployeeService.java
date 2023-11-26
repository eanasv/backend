package com.test.apiTest.service;


import com.test.apiTest.dto.*;
import com.test.apiTest.model.*;
import com.test.apiTest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    TrainingNeedRepository trainingNeedRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private EmployeeICTRoleRepo employeeICTRoleRepo;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private EmployeeEvaluationRepo employeeEvaluationRepo;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SoftSkillRepository softSkillRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;


    public List<EmployeeICTRole> getAllEmployeeICT() {
        return employeeICTRoleRepo.findAll();
    }

//    public List<Course> getAllCourse(){
//        return courseRepo.findAll();
//    }

    public List<EmployeeEvaluation> getAllEmployeeEvaluatio() {
        return employeeEvaluationRepo.findAll();
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


//    public List<Map<String, Object>> getNumberOfCategoriesInAll() {
//        List<Object[]>  categoryCount = employeeRepository.findNumberOfEmployeesInEachCategory();
//        List<Map<String, Object>> counts = new ArrayList<>();
//        for (Object[] row : categoryCount) {
//           Integer id = (Integer) row[0];// in remote server
//           //Long id = (Long) row[0];// only work in local server
//            String category = (String) row[1];
//            Long count = (Long) row[2];
//            Map<String, Object> countMap = new HashMap<>();
//            countMap.put("category", category);
//            countMap.put("count", count);
//            countMap.put("id",id);
//            counts.add(countMap);
//        }
//        return counts;
//    }

    public List<Map<String, Object>> getNumberOfCategories(String entityName) {
        List<Object[]> categoryCount;

        if (entityName != null && !entityName.isEmpty()) {
            // If entityName is provided, fetch counts for the specified entity
            categoryCount = employeeRepository.findNumberOfEmployeesInEachCategoryForEntity(entityName);
            // categoryCount = employeeRepository.findNumberOfEmployeesInEachCategory();
        } else {
            // If no entityName is provided, fetch counts for all entities
            categoryCount = employeeRepository.findNumberOfEmployeesInEachCategory();
        }


//
//        List<Map<String, Object>> counts = new ArrayList<>();
//        for (Object[] row : categoryCount) {
//            //Integer id = (Integer) row[0];// in remote server
//            Long id = (Long) row[0];// only work in local server
//            String category = (String) row[1];
//            Long count = (Long) row[2];
//            Map<String, Object> countMap = new HashMap<>();
//            countMap.put("category", category);
//            countMap.put("count", count);
//            countMap.put("id", id);
//            counts.add(countMap);
//        }


        // Create a map to store counts for each category
        Map<Long, Long> categoryCountsMap = new HashMap<>();
        for (Object[] row : categoryCount) {
            Long id = (Long) row[0];
            Long count = (Long) row[2];
            categoryCountsMap.put(id, count);
        }

        // Fetch all categories from the database
        List<Category> allCategories = categoryRepository.findAll(); // Assuming you have a Category entity

        // Create the response list with category IDs, names, and counts
        List<Map<String, Object>> counts = new ArrayList<>();
        for (Category category : allCategories) {
            Long id = category.getId();
            String categoryName = category.getName();

            // Check if the category has a count, if not, set count to 0
            Long count = categoryCountsMap.getOrDefault(id, 0L);

            Map<String, Object> countMap = new HashMap<>();
            countMap.put("id", id);
            countMap.put("category", categoryName);
            countMap.put("count", count);
            counts.add(countMap);
        }
        return counts;
    }


//    public List<Map<String, Object>> getNumberOfCategories(String entityName) {
//        List<Object[]> categoryCount;
//
//        if (entityName != null && !entityName.isEmpty()) {
//            // If entityName is provided, fetch counts for the specified entity
//            categoryCount = employeeRepository.findNumberOfEmployeesInEachCategoryForEntity(entityName);
//        } else {
//            // If no entityName is provided, fetch counts for all entities
//            categoryCount = employeeRepository.findNumberOfEmployeesInEachCategory();
//        }
//
//        Set<String> existingCategories = new HashSet<>();
//        List<Map<String, Object>> counts = new ArrayList<>();
//
//        // Iterate over rows returned from the database and create a set of existing categories
//        for (Object[] row : categoryCount) {
//            existingCategories.add((String) row[1]);
//        }
//
//        // Iterate over all categories and add them to the result with count 0 if not found in the database result
//        for (String category : getAllCategories()) {
//            if (!existingCategories.contains(category)) {
//                Map<String, Object> countMap = new HashMap<>();
//                countMap.put("category", category);
//                countMap.put("count", 0L); // Set count to 0 for categories not found in the database result
//                countMap.put("id", null); // You can set id to null or any appropriate value
//                counts.add(countMap);
//            }
//        }
//
//        // Add categories from the database result to the final result
//        for (Object[] row : categoryCount) {
//            Long id = (Long) row[0];
//            String category = (String) row[1];
//            Long count = (Long) row[2];
//            Map<String, Object> countMap = new HashMap<>();
//            countMap.put("category", category);
//            countMap.put("count", count);
//            countMap.put("id", id);
//            counts.add(countMap);
//        }
//
//        return counts;
//    }

    // Method to retrieve all categories (you need to implement this based on your data source)
    public Set<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        Iterable<Category> categoryEntities = categoryRepository.findAll();
        for (Category category : categoryEntities) {
            categories.add(category.getName().toLowerCase()); // Ensure lowercase retrieval
        }
        return categories;
    }

    public List<Map<String, Object>> getNumberOfCategoriesInOneEntity(String entityName) {
        List<Object[]> categoryCount = employeeRepository.findNumberOfCategoryInEntity(entityName);
        List<Map<String, Object>> counts = new ArrayList<>();
        for (Object[] row : categoryCount) {
            String category = (String) row[0];
            Long count = (Long) row[1];
            Map<String, Object> countMap = new HashMap<>();
            countMap.put("category", category);
            countMap.put("count", count);
            counts.add(countMap);
        }
        return counts;
    }


    public List<CategoryWiseScore> getScoresByCategoryAndCompany(String entity) {
        String sql = "SELECT e.category, SUM(s.score) AS total_score FROM employee e " +
                "JOIN skill s ON e.employee_number = s.employee_number " +
                "WHERE e.entity = ? AND e.category IN ('ICT', 'Data Analytics', 'Cyber security') " +
                "GROUP BY e.category";
        List<CategoryWiseScore> categoryScores = jdbcTemplate.query(sql, new Object[]{entity},
                (rs, rowNum) -> new CategoryWiseScore(rs.getString("category"), rs.getInt("total_score")));
        return categoryScores;
    }

    public List<EmployeeDto> getAllEmployeesWithSkills() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeNumber(employee.getEmployeeNumber());
            employeeDto.setCategory(employee.getCategory());
            employeeDto.setSubCategory(employee.getSubCategory());
            employeeDto.setJob(employee.getJob());

            employeeDto.setEntity(employee.getEntity());
            List<Skill> skills = skillRepository.findByEmployeeNumber(employee.getEmployeeNumber());
            List<SkillDto> skillDtos = new ArrayList<>();
            for (Skill skill : skills) {
                SkillDto skillDto = new SkillDto();
                skillDto.setName(skill.getName());
                //skillDto.setScore(skill.getScore());
                skillDtos.add(skillDto);
            }
            employeeDto.setSkills(skillDtos);
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    public List<EmployeeSkillDto> getEmployeesByEntity(String entityName) {//
        List<Employee> employees = employeeRepository.findByEntity(entityName);
        List<EmployeeSkillDto> employeeSkillDtos = new ArrayList<>();
        for (Employee employee : employees) {
            int totalAchievedSkills = 0;
            EmployeeSkillDto employeeSkillDto = new EmployeeSkillDto();
            employeeSkillDto.setEmployeeNumber(employee.getEmployeeNumber());
            employeeSkillDto.setName(employee.getName().toLowerCase());
            employeeSkillDto.setCategory(employee.getCategory().toLowerCase());
            employeeSkillDto.setSubCategory(employee.getSubCategory());
            employeeSkillDto.setJob(employee.getJob().toLowerCase());

            employeeSkillDto.setEntity(employee.getEntity());
            // get all subcategories for the employee's category
            Integer categoryId = categoryRepository.findByName(employee.getCategory());
            List<Role> roles = roleRepository.findBySubcategoryId(subcategoryRepository.findByName(employee.getSubCategory()), employee.getJob());
            List<RoleLevelTreeDto> roleLevelTreeDtos = new ArrayList<>();
            List<TrainingNeedDTO> trainingNeedDTOS = new ArrayList<>();
            List<String> technicalSkillNames = skillRepository.findTechnicalSkillNamesByRoleNames(employee.getJob());
            List<String> softSkillNames = softSkillRepository.findSoftSkillNamesByRoleNames(employee.getJob());
            List<TrainingNeed> trainingNeeds = trainingNeedRepository.findAllByEmployee_EmployeeNumber(Long.valueOf(employee.getEmployeeNumber()));

            for (TrainingNeed trainingNeed : trainingNeeds) {
                TrainingNeedDTO trainingNeedDTO = new TrainingNeedDTO();
                List<Course> courses = courseRepository.findByTrainingNeedId(trainingNeed.getId());
                List<CourseDTO> courseDTOS = courses.stream()
                        .map(course -> new CourseDTO(course.getCourse(), course.getEnrollmentDate(), course.getEnrollmentStatus()))
                        .collect(Collectors.toList());
                trainingNeedDTO.setCourses(courseDTOS);
                trainingNeedDTO.setLinkedCompetency(trainingNeed.getLinkedCompetency().toLowerCase());
                trainingNeedDTOS.add(trainingNeedDTO);

            }
//           List<TrainingNeedDTO> trainingNeedDTOS= trainingNeeds.stream()
//                    .map(trainingNeed -> new TrainingNeedDTO( trainingNeed.getLinkedCompetency(), trainingNeed.getCourses()))
//                    .collect(Collectors.toList());
            employeeSkillDto.setTrainingNeeds(trainingNeedDTOS);
            employeeSkillDto.setSoftSkills(softSkillNames);
            employeeSkillDto.setTechnicalSkills(technicalSkillNames);

//            int totalSkills = softSkillNames.size() + technicalSkillNames.size();
            int totalSkills = skillRepository.findCountByEmployeeNumber(employee.getEmployeeNumber());
//
            for (Role role : roles) {
                RoleLevelTreeDto roleLevelTreeDto = new RoleLevelTreeDto();
                //roleLevelTreeDto.setId(roles.getId());
                roleLevelTreeDto.setName(role.getName().toLowerCase());
                //System.out.println(employee.getName() +"---------------"+role.getName());
                roleLevelTreeDto.setActive(false);
                if (employee.getJob().equalsIgnoreCase(role.getName())) {
                    roleLevelTreeDto.setActive(true);

                    // roleLevelTreeDto.setTechnicalSkills(technicalSkillNames);
                }
                roleLevelTreeDtos.add(roleLevelTreeDto);
            }

            List<Skill> skills = skillRepository.findByEmployeeNumber(employee.getEmployeeNumber());
            List<SkillDto> skillDtos = new ArrayList<>();
//            for (Skill skill : skills) {
//                //totalSkills++;
//                SkillDto skillDto = new SkillDto();
//                skillDto.setName(skill.getName().toLowerCase());
//                skillDto.setAchievedStatus(skill.getEvaluationResult());
//                if ("achieved".equalsIgnoreCase(skill.getEvaluationResult())) {
//                    totalAchievedSkills++;
//                }
//                skillDtos.add(skillDto);
//            }

            List<SkillDto> technicalSkillsWithStatus = new ArrayList<>();
            List<SkillDto> softSkillsWithStatus = new ArrayList<>();
            List<SkillDto> otherSkillsWithStatus = new ArrayList<>();
            for (Skill skill : skills) {
                String skillName = skill.getName().toLowerCase();
                String achievedStatus = skill.getEvaluationResult();

                SkillDto skillDto = new SkillDto();
                skillDto.setName(skillName);
                skillDto.setAchievedStatus(achievedStatus);

                if ("achieved".equalsIgnoreCase(skill.getEvaluationResult())) {
                    totalAchievedSkills++;
                }
                if (isTechnicalSkill(skillName) == 1) {
                    technicalSkillsWithStatus.add(skillDto);
                } else if (isSoftSkill(skillName) == 1) {
                    softSkillsWithStatus.add(skillDto);
                } else {
                    otherSkillsWithStatus.add(skillDto);
                }

                // ... (existing code)
            }

            Map<String, List<SkillDto>> skillsMap = new HashMap<>();
            skillsMap.put("technical_skills", technicalSkillsWithStatus);
            skillsMap.put("soft_skills", softSkillsWithStatus);
            skillsMap.put("other_skills", otherSkillsWithStatus);
//            employeeSkillDto.setSkills((List<SkillDto>) skillsMap);
            employeeSkillDto.setSkills(skillsMap);


            if (totalSkills > 0) {
                double scorePercentage = ((double) totalAchievedSkills / (double) totalSkills) * 100;
                employeeSkillDto.setEmployeeSkill((long) scorePercentage);
            } else if (totalSkills == 0) {
                employeeSkillDto.setEmployeeSkill((long) 0);
            }
            employeeSkillDto.setRoleTree(roleLevelTreeDtos);
            //employeeSkillDto.setSkills(skillDtos);
            employeeSkillDtos.add(employeeSkillDto);
        }
        return employeeSkillDtos;

    }


    private Integer isTechnicalSkill(String skillName) {
        // Implement logic to check if the skill is present in the technical_skills table
        // Example: Assuming technicalSkillRepository is your Spring Data JPA repository for technical_skills table
        return technicalSkillRepository.existsByName(skillName);
    }

    private Integer isSoftSkill(String skillName) {
        // Implement logic to check if the skill is present in the soft_skills table
        // Example: Assuming softSkillRepository is your Spring Data JPA repository for soft_skills table
        return softSkillRepository.existsByName(skillName);
    }

    private List<SkillDto> createSkillDtos(Map<String, String> skillsWithStatus) {
        return skillsWithStatus.entrySet().stream()
                .map(entry -> {
                    SkillDto skillDto = new SkillDto();
                    skillDto.setName(entry.getKey());
                    skillDto.setAchievedStatus(entry.getValue());
                    return skillDto;
                })
                .collect(Collectors.toList());
    }


    public List<Map<String, Object>> getSKillByCatInEntity(String entity) {
        List<Map<String, Object>> result = employeeRepository.getCategoryScoreCounts(entity);
        List<Map<String, Object>> counts = new ArrayList<>();
        for (Map<String, Object> row : result) {
            String category = (String) row.get("category");
            Long totalNumberOfSkills = (Long) row.get("total_count");

            Long totalAchieved = (Long) row.get("achieved_count");

            BigDecimal avgScorePercentage = BigDecimal.valueOf(0);
            BigDecimal achievedCount = BigDecimal.valueOf(totalAchieved.longValue());

            BigDecimal totalCount = BigDecimal.valueOf(totalNumberOfSkills);

            if (totalCount.compareTo(BigDecimal.ZERO) > 0) {
                avgScorePercentage = achievedCount.multiply(BigDecimal.valueOf(100))
                        .divide(totalCount, 2, RoundingMode.HALF_UP);
                System.out.println("Category: " + category + ", Score Percentage: " + avgScorePercentage + "%");
            }

            Map<String, Object> countMap = new HashMap<>();
            countMap.put("category", category);
            countMap.put("avgachieved_score", avgScorePercentage);
            counts.add(countMap);
        }

        return counts;
    }


    public List<Employee> getEmployeesByEntityAndSubcat(String entity, String subcat) {
        List<Employee> employees = employeeRepository.findByEntityAndSubCat(entity, subcat);
        return employees;
    }

    public List<Employee> getEmployeesByLevelAndSubcat(String level, String subcat) {
        List<Employee> employees = employeeRepository.findByLevelAndSubCat(level, subcat);
        return employees;
    }

    public List<Employee> getEmployeesBySubcat(String subcat) {
        List<Employee> employees = employeeRepository.findBySubCat(subcat);
        return employees;
    }

    public List<Employee> getEmployeesBySubcatAndEntity(String subcat, String entity) {
        List<Employee> employees = employeeRepository.findBySubCatAndEntity(subcat, entity);
        return employees;
    }

    public List<Employee> getEmployeesByLevelAndSubcatAndEntity(String level, String subcat, String entity) {
        System.out.println("SELECT * FROM employee where  level =" + level + " and sub_category=" + subcat + " and entity=" + entity);
        List<Employee> employees = employeeRepository.findByLevelAndSubcatAndEntity(level, subcat, entity);
        return employees;
    }


}
