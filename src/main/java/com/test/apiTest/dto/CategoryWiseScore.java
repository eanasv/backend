package com.test.apiTest.dto;

public class CategoryWiseScore {
    private String category;
    private int totalScore;

    public CategoryWiseScore(String category, int totalScore) {
        this.category = category;
        this.totalScore = totalScore;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
