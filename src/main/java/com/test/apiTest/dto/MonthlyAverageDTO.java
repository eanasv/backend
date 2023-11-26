package com.test.apiTest.dto;

import java.math.BigDecimal;

public class MonthlyAverageDTO {

    private String month;
    private BigDecimal averageScore;

    public MonthlyAverageDTO(String month, BigDecimal averageScore) {
        this.month = month;
        this.averageScore = averageScore;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = BigDecimal.valueOf(averageScore);
    }
}
