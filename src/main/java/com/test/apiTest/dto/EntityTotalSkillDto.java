package com.test.apiTest.dto;

public class EntityTotalSkillDto {
    private String label;
    private String value;
    private byte[] image;

    private String maxDate;
    private double totalSkillPercentage;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getTotalSkillPercentage() {
        return totalSkillPercentage;
    }

    public void setTotalSkillPercentage(double totalSkillPercentage) {
        this.totalSkillPercentage = totalSkillPercentage;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }
}
