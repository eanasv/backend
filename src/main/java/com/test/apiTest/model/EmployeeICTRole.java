package com.test.apiTest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EmployeeICTRole {
    @Id
    private Integer employee_number;
    private String employee_name;
    private String job;
    private String organization;
    private String ict_role;

    public EmployeeICTRole(Integer employee_number, String employee_name, String job, String organization, String ict_role) {
        this.employee_number = employee_number;
        this.employee_name = employee_name;
        this.job = job;
        this.organization = organization;
        this.ict_role = ict_role;
    }

    public EmployeeICTRole() {
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
}
