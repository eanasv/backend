package com.test.apiTest.dto;

import com.test.apiTest.model.Employee;

import java.util.List;

public class EntityWithEmployeesDto {
    private final Long id;
    private final String name;

    private final byte[] image;

    private final List<Employee> employees;

    public EntityWithEmployeesDto(Long id, String name, byte[] image, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.employees = employees;
    }


}
