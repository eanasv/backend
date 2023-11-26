package com.test.apiTest.dto;

import java.util.List;

public class CompanyAverageDTO {

    private String companyName;
    private List<MonthlyAverageDTO> monthlyAverages;

    public CompanyAverageDTO(String companyName, List<MonthlyAverageDTO> monthlyAverages) {
        this.companyName = companyName;
        this.monthlyAverages = monthlyAverages;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<MonthlyAverageDTO> getMonthlyAverages() {
        return monthlyAverages;
    }

    public void setMonthlyAverages(List<MonthlyAverageDTO> monthlyAverages) {
        this.monthlyAverages = monthlyAverages;
    }
}
