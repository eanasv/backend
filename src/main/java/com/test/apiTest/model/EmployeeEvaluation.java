package com.test.apiTest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class EmployeeEvaluation {
    @Id
    private Integer employee_number;
    private String employee_name;
    private String job;
    private String organization;
    private String ict_role;
    private String ict_competency;
    private String evaluation_result;
    private Date evaluation_date;

    public EmployeeEvaluation(Integer employee_number, String employee_name, String job, String organization, String ict_role, String ict_competency, String evaluation_result, Date evaluation_date) {
        this.employee_number = employee_number;
        this.employee_name = employee_name;
        this.job = job;
        this.organization = organization;
        this.ict_role = ict_role;
        this.ict_competency = ict_competency;
        this.evaluation_result = evaluation_result;
        this.evaluation_date = evaluation_date;
    }

    public EmployeeEvaluation() {
    }

    public Integer getEmployee_number() {
        return employee_number;
    }

    public void setEmployee_number(Integer employee_number) {
        this.employee_number = employee_number;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getIct_role() {
        return ict_role;
    }

    public void setIct_role(String ict_role) {
        this.ict_role = ict_role;
    }

    public String getIct_competency() {
        return ict_competency;
    }

    public void setIct_competency(String ict_competency) {
        this.ict_competency = ict_competency;
    }

    public String getEvaluation_result() {
        return evaluation_result;
    }

    public void setEvaluation_result(String evaluation_result) {
        this.evaluation_result = evaluation_result;
    }

    public Date getEvaluation_date() {
        return evaluation_date;
    }

    public void setEvaluation_date(Date evaluation_date) {
        this.evaluation_date = evaluation_date;
    }
}
