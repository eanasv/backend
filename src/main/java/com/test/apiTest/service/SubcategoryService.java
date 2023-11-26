package com.test.apiTest.service;

import com.test.apiTest.dto.CategoryDto;
import com.test.apiTest.model.Subcategory;
import com.test.apiTest.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<CategoryDto> getAllSubCatsOfMainCat(int categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(categoryId);
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Subcategory myEntity : subcategories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId((long) myEntity.getId());
            categoryDto.setLabel(myEntity.getName());
            categoryDto.setValue(myEntity.getName());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
        //  return subcategories;

    }


//    public List<Object[]> findSubcategoryEmployeeCountByCategoryId(Long categoryId) {
//    }
}
