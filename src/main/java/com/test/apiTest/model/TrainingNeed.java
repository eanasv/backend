package com.test.apiTest.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "training_needs")
public class TrainingNeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "linked_competency")
    private String linkedCompetency;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_number", referencedColumnName = "employee_number")
    private Employee employee;

    @OneToMany(mappedBy = "trainingNeed", cascade = CascadeType.ALL)
    private List<Course> courses;

    // Constructors, getters, and setters

    public TrainingNeed() {
    }

    public TrainingNeed(String linkedCompetency, Employee employee) {
        this.linkedCompetency = linkedCompetency;
        this.employee = employee;
    }

    // Getters and Setters omitted for brevity


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLinkedCompetency() {
        return linkedCompetency;
    }

    public void setLinkedCompetency(String linkedCompetency) {
        this.linkedCompetency = linkedCompetency;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

