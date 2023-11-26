package com.test.apiTest.dto;

public class AverageSkillOfEntityDto {
    private String entityName;
    private Double averageSkill;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Double getAverageSkill() {
        return averageSkill;
    }

    public void setAverageSkill(Double averageSkill) {
        this.averageSkill = averageSkill;
    }
}
