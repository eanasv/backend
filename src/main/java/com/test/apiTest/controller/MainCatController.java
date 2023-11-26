package com.test.apiTest.controller;

import com.test.apiTest.dto.CategoryDto;
import com.test.apiTest.model.Category;
import com.test.apiTest.model.Subcategory;
import com.test.apiTest.repository.CategoryRepository;
import com.test.apiTest.repository.SubcategoryRepository;
import com.test.apiTest.service.MainCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/main-categories")
public class MainCatController {
    @Autowired
    private MainCatService mainCatService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @GetMapping("/getAll")
    public List<CategoryDto> getAllMainCats() {
        return mainCatService.getAllMainCats();
    }

//    @GetMapping("/level-counts")
//    public List<Map<String, Object>> getLevelCountsByCategory() {
//        List<Map<String, Object>> result = new ArrayList<>();
//        List<Category> categories = categoryRepository.findAll();
//        for (Category category : categories) {
//            Map<String, Object> categoryMap = new HashMap<>();
//            categoryMap.put("categoryName", category.getName());
//            List<Map<String, Object>> subcategoryList = new ArrayList<>();
//            List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(Math.toIntExact(category.getId()));
//            for (Subcategory subcategory : subcategories) {
//                Map<String, Object> subcategoryMap = new HashMap<>();
//                subcategoryMap.put("id", subcategory.getId());
//                subcategoryMap.put("category", subcategory.getName());
//                List<Integer> levelCounts = new ArrayList<>();
//                Object[] row = categoryRepository.findAllCategorySubcategoryEmployeeCountsByLevel((long) subcategory.getId()).get(0);
//                for (int i = 2; i <= 10; i++) {
//                    if (row[i] instanceof Number) {
//                        levelCounts.add(((Number) row[i]).intValue());
//                    } else if (row[i] instanceof String) {
//                        levelCounts.add(Integer.parseInt((String) row[i]));
//                    } else {
//                        // Handle unexpected type here
//                    }
//                    //levelCounts.add(((Number) row[i]).intValue());
//                   // levelCounts[i] = ((Number) arr[i + 2]).intValue();
//                }
//                subcategoryMap.put("levelCounts", levelCounts);
//                subcategoryMap.put("count", ((Number) row[11]).intValue());
//                subcategoryMap.put("grantTotalEmp", ((Number) row[12]).intValue());
//                subcategoryList.add(subcategoryMap);
//            }
//            categoryMap.put("levelDetail", subcategoryList);
//            result.add(categoryMap);
//        }
//        return result;
//    }

    @GetMapping("/level-counts")
    public List<Map<String, Object>> getLevelCountsByCategory() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("categoryName", category.getName());
            List<Map<String, Object>> subcategoryList = new ArrayList<>();
            List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(Math.toIntExact(category.getId()));
            for (Subcategory subcategory : subcategories) {
                Map<String, Object> subcategoryMap = new HashMap<>();
                subcategoryMap.put("id", subcategory.getId());
                subcategoryMap.put("category", subcategory.getName());
                List<Integer> levelCounts = new ArrayList<>();
                List<Object[]> data = categoryRepository.findAllCategorySubcategoryEmployeeCountsByLevel(subcategory.getId());
                if (!data.isEmpty()) {
                    Object[] row = data.get(0);
                    for (int i = 2; i <= 10; i++) {
                        levelCounts.add(((Number) row[i]).intValue());
                    }
                    subcategoryMap.put("levelCounts", levelCounts);
                    subcategoryMap.put("count", ((Number) row[11]).intValue());
                    subcategoryMap.put("grantTotalEmp", ((Number) row[12]).intValue());
                }
                subcategoryList.add(subcategoryMap);
            }
            categoryMap.put("levelDetail", subcategoryList);
            result.add(categoryMap);
        }
        return result;
    }

}
