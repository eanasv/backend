package com.test.apiTest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_number")
    private String employeeNumber;


    private String name;

    private String job;

    private String entity;

    private Integer level;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "entity", referencedColumnName = "name")
//    private Entities entities;

    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    //private String role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Skill> skills;


}
