package com.test.apiTest.service;

import com.test.apiTest.dto.CategoryDto;
import com.test.apiTest.model.Category;
import com.test.apiTest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainCatService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getAllMainCats() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category myEntity : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(myEntity.getId());
            categoryDto.setLabel(myEntity.getName());
            categoryDto.setValue(myEntity.getName());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;

    }
}
