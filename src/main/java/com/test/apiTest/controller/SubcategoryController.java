package com.test.apiTest.controller;


import com.test.apiTest.dto.SubcategoryEmployeeCountsDto;
import com.test.apiTest.repository.CategoryRepository;
import com.test.apiTest.repository.SubcategoryRepository;
import com.test.apiTest.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class SubcategoryController {
    @Autowired
    SubcategoryRepository subcategoryRepository;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private CategoryRepository categoryRepository;
//working
//    @GetMapping("/{categoryId}/subcategories")
//    public List<CategoryDto> getSubcategoriesByCategoryId(@PathVariable int categoryId) {
//        return subcategoryService.getAllSubCatsOfMainCat(categoryId);
//    }
//    @GetMapping("/{categoryId}/subcategories")
//    public ResponseEntity<List<SubcategoryEmployeeCountsDto>> getSubcategoriesByCategoryId(@PathVariable Integer categoryId) {
//        List<Object[]> subcategoryLevelCounts = subcategoryRepository.findSubcategoryEmployeeCountsByLevel(categoryId);
//
//        List<SubcategoryEmployeeCountsDto> result = new ArrayList<>();
//        for (Object[] arr : subcategoryLevelCounts) {
//            Integer subcategoryId = (Integer) arr[0];
//            String subcategoryName = ((String) arr[1]).toLowerCase();
//            Integer[] levelCounts = new Integer[9];
//            Long count = (Long) arr[11];
//            Long grantTotal = (Long) arr[12];
//            for (int i = 0; i < 9; i++) {
//                levelCounts[i] = ((Number) arr[i + 2]).intValue();
//            }
//
//            SubcategoryEmployeeCountsDto dto = new SubcategoryEmployeeCountsDto(subcategoryId, subcategoryName, levelCounts,count,grantTotal);
//            result.add(dto);
//        }
//
//        return ResponseEntity.ok(result);
//    }

    @GetMapping({"/{categoryId}/subcategories"})
    public ResponseEntity<List<SubcategoryEmployeeCountsDto>> getSubcategoriesByCategoryId(@PathVariable Integer categoryId,
                                                                                           @RequestParam(required = false) String entityName) {
        List<Object[]> subcategoryLevelCounts = new ArrayList<>();
        if (entityName != null && !entityName.isEmpty()) {
            // Fetch counts for the specified entity and category
            subcategoryLevelCounts = subcategoryRepository.findSubcategoryEmployeeCountsByLevelAndEntity(categoryId, entityName);
        } else {
            // Fetch counts for all entities in the category
            subcategoryLevelCounts = subcategoryRepository.findSubcategoryEmployeeCountsByLevel(categoryId);
        }

        // List<Object[]> subcategoryLevelCounts = this.subcategoryRepository.findSubcategoryEmployeeCountsByLevel(categoryId);
        List<SubcategoryEmployeeCountsDto> result = new ArrayList();
        Iterator var4 = subcategoryLevelCounts.iterator();

        while (var4.hasNext()) {
            Object[] arr = (Object[]) var4.next();
            Integer subcategoryId = (Integer) arr[0];
            String subcategoryName = ((String) arr[1]).toLowerCase();
            Integer[] levelCounts = new Integer[9];
            Long count = (Long) arr[11];
            Long grantTotal = (Long) arr[12];

            for (int i = 0; i < 9; ++i) {
                levelCounts[i] = ((Number) arr[i + 2]).intValue();
            }

            SubcategoryEmployeeCountsDto dto = new SubcategoryEmployeeCountsDto(subcategoryId, subcategoryName, levelCounts, count, grantTotal);
            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }


    private Integer[] getLevelCountsArray(Object[] arr) {
        Integer[] levelCounts = new Integer[9];
        for (int i = 0; i < 9; i++) {
            levelCounts[i] = ((Number) arr[i + 2]).intValue();
        }
        return levelCounts;
    }

    private String getCategoryNameById(Integer categoryId) {
        // Implement your logic to retrieve the category name based on the ID
        // You can query the database or use a predefined mapping
        // Return the category name as a String
        String categoryName = categoryRepository.findNameById(categoryId);
        return categoryName;
    }


    @GetMapping("/subcategories")
    public ResponseEntity<List<Map<String, Object>>> getAllSubcategoriesByCategory(
            @RequestParam(required = false) String entityName) {
        //@RequestParam(required = false) String entityName

        List<Integer> categoryIds = Arrays.asList(1, 2, 3);
        List<Map<String, Object>> response = new ArrayList<>();

        Long overallEmployees = 0L;
        for (Integer categoryId : categoryIds) {
            String categoryName = getCategoryNameById(categoryId);
            List<Object[]> subcategoryLevelCounts;

            if (entityName != null && !entityName.isEmpty()) {
                // Fetch counts for the specified entity and category
                subcategoryLevelCounts = subcategoryRepository.findSubcategoryEmployeeCountsByLevelAndEntity(categoryId, entityName);
            } else {
                // Fetch counts for all entities in the category
                subcategoryLevelCounts = subcategoryRepository.findSubcategoryEmployeeCountsByLevel(categoryId);
            }

            List<Map<String, Object>> subcategories = new ArrayList<>();

            for (Object[] arr : subcategoryLevelCounts) {
                Map<String, Object> subcategory = new HashMap<>();
                subcategory.put("id", arr[0]);
                subcategory.put("subcategory", ((String) arr[1]).toLowerCase());
                subcategory.put("levelCounts", getLevelCountsArray(arr));
                subcategory.put("count", arr[11]);
                //subcategory.put("grantTotalEmp", arr[12]);
                overallEmployees = overallEmployees + (Long) arr[12];
                subcategories.add(subcategory);
            }

            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("categoryname", categoryName);
            categoryMap.put("details", subcategories);
            categoryMap.put("overallEmployee", overallEmployees);
            response.add(categoryMap);
        }

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{categoryId}/subcategory/{subcategoryId}")
//    public ResponseEntity<List<Map<String, Object>>> getEntitiesByCategoryId(@PathVariable Long categoryId,@PathVariable Long subcategoryId) {
//        List<Object[]> entityDetails = subcategoryRepository.findEntityEmployeeCountByCategoryId(categoryId,subcategoryId);
//        List<Map<String, Object>> result = entityDetails.stream().map(arr -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put("entityName", arr[0]);
//            map.put("employeeNumber", arr[1]);
//            return map;
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/{categoryId}/subcategory/{subcategoryId}")
    public ResponseEntity<List<Map<String, Object>>> getEntitiesByCategoryId(
            @PathVariable Long categoryId,
            @PathVariable Long subcategoryId,
            @RequestParam(required = false) String entityName) {

        List<Object[]> entityDetails;

        if (entityName != null && !entityName.isEmpty()) {
            // Fetch entity details by categoryId, subcategoryId, and entityName
            entityDetails = subcategoryRepository.findEntityEmployeeCountByCategoryIdAndSubcategoryIdAndEntityName(categoryId, subcategoryId, entityName);
        } else {
            // Fetch entity details by categoryId and subcategoryId
            entityDetails = subcategoryRepository.findEntityEmployeeCountByCategoryIdAndSubcategoryId(categoryId, subcategoryId);
        }

        List<Map<String, Object>> result = entityDetails.stream().map(arr -> {
            Map<String, Object> map = new HashMap<>();
            map.put("entityName", arr[0]);
            map.put("employeeNumber", arr[1]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


}

