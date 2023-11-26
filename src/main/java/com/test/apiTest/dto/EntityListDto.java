package com.test.apiTest.dto;

public class EntityListDto {
    private String label;
    private byte[] image;

    public EntityListDto(byte[] image, String label, double scorePercentage) {
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
}
