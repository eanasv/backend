package com.test.apiTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeSkillDto {

    private String employeeNumber;
    private String entity;

    private String name;
    private String job;
    private String category;
    private String subCategory;
    private List<RoleLevelTreeDto> roleTree;
//    private String role;
//
//    private Integer roleId;

    private List<String> technicalSkills;

    private List<String> softSkills;

    private List<TrainingNeedDTO> trainingNeeds;
    // private List<SkillDto> skills;
    private Map<String, List<SkillDto>> skills;

    private Long employeeSkill;

//    public List<TrainingNeedDTO> getTrainingNeeds() {
//        return trainingNeeds;
//    }
//
//    public void setTrainingNeeds(List<TrainingNeedDTO> trainingNeeds) {
//        this.trainingNeeds = trainingNeeds;
//    }
//
//    public List<String> getSoftSkills() {
//        return softSkills;
//    }
//
//    public void setSoftSkills(List<String> softSkills) {
//        this.softSkills = softSkills;
//    }
//
////    public Integer getRoleId() {
////        return roleId;
////    }
////
////    public void setRoleId(Integer roleId) {
////        this.roleId = roleId;
////    }
//
//    public List<String> getTechnicalSkills() {
//        return technicalSkills;
//    }
//
//    public void setTechnicalSkills(List<String> technicalSkills) {
//        this.technicalSkills = technicalSkills;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<RoleLevelTreeDto> getRoleTree() {
//        return roleTree;
//    }
//
//    public void setRoleTree(List<RoleLevelTreeDto> roleTree) {
//        this.roleTree = roleTree;
//    }
//
//    public List<RoleLevelTreeDto> getSubcategories() {
//        return roleTree;
//    }
//
//    public void setSubcategories(List<RoleLevelTreeDto> subcategories) {
//        this.roleTree = subcategories;
//    }
//
//    public Integer getEmployeeNumber() {
//        return employeeNumber;
//    }
//
//    public void setEmployeeNumber(Integer employeeNumber) {
//        this.employeeNumber = employeeNumber;
//    }
//
//    public String getEntity() {
//        return entity;
//    }
//
//    public void setEntity(String entity) {
//        this.entity = entity;
//    }
//
//    public String getJob() {
//        return job;
//    }
//
//    public void setJob(String job) {
//        this.job = job;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getSubCategory() {
//        return subCategory;
//    }
//
//    public void setSubCategory(String subCategory) {
//        this.subCategory = subCategory;
//    }
//
////    public String getRole() {
////        return role;
////    }
////
////    public void setRole(String role) {
////        this.role = role;
////    }
//
//    public List<SkillDto> getSkills() {
//        return skills;
//    }
//
//    public void setSkills(List<SkillDto> skills) {
//        this.skills = skills;
//    }
//
//    public Long getEmployeeSkill() {
//        return employeeSkill;
//    }
//
//    public void setEmployeeSkill(Long employeeSkill) {
//        this.employeeSkill = employeeSkill;
//    }


}
