package com.test.apiTest.response;

import com.test.apiTest.dto.CategoryWiseScore;
import com.test.apiTest.dto.EmployeeSkillDto;

import java.util.List;
import java.util.Map;

public class ApiResponseInEntity {
    private List<CategoryWiseScore> categoryWiseScoreList;
    private List<Map<String, Object>> categoryCountInEntities;
    private List<EmployeeSkillDto> employeeDetailsInEntity;

    public ApiResponseInEntity(List<CategoryWiseScore> categoryWiseScoreList, List<Map<String, Object>> categoryCountInEntities, List<EmployeeSkillDto> employeeDetailsInEntity) {
        this.categoryWiseScoreList = categoryWiseScoreList;
        this.categoryCountInEntities = categoryCountInEntities;
        this.employeeDetailsInEntity = employeeDetailsInEntity;
    }

    public List<CategoryWiseScore> getCategoryWiseScoreList() {
        return categoryWiseScoreList;
    }

    public void setCategoryWiseScoreList(List<CategoryWiseScore> categoryWiseScoreList) {
        this.categoryWiseScoreList = categoryWiseScoreList;
    }

    public List<Map<String, Object>> getCategoryCountInEntities() {
        return categoryCountInEntities;
    }

    public void setCategoryCountInEntities(List<Map<String, Object>> categoryCountInEntities) {
        this.categoryCountInEntities = categoryCountInEntities;
    }

    public List<EmployeeSkillDto> getEmployeeDetailsInEntity() {
        return employeeDetailsInEntity;
    }

    public void setEmployeeDetailsInEntity(List<EmployeeSkillDto> employeeDetailsInEntity) {
        this.employeeDetailsInEntity = employeeDetailsInEntity;
    }
}
