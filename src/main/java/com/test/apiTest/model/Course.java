package com.test.apiTest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course")
    private String courseName;
    @Column(name = "enrollment_date")
    private String enrollmentDate;
    @Column(name = "enrollment_status")
    private String enrollmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_need_id")
    private TrainingNeed trainingNeed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse() {
        return courseName;
    }

    public void setCourse(String course) {
        this.courseName = course;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public TrainingNeed getTrainingNeed() {
        return trainingNeed;
    }

    public void setTrainingNeed(TrainingNeed trainingNeed) {
        this.trainingNeed = trainingNeed;
    }

    //    @Id
//    private Integer Employee_number;
//    private String Employee_name;
//    private String Course;
//    private String Linked_competency;
//    private String Enrollment_date;
//    private String Enrollment_status;
//
//    public Course(Integer employee_number, String employee_name, String course, String linked_competency, String enrollment_date, String enrollment_status) {
//        Employee_number = employee_number;
//        Employee_name = employee_name;
//        Course = course;
//        Linked_competency = linked_competency;
//        Enrollment_date = enrollment_date;
//        Enrollment_status = enrollment_status;
//    }
//
//    public Course() {
//    }
//
//    public Integer getEmployee_number() {
//        return Employee_number;
//    }
//
//    public void setEmployee_number(Integer employee_number) {
//        Employee_number = employee_number;
//    }
//
//    public String getEmployee_name() {
//        return Employee_name;
//    }
//
//    public void setEmployee_name(String employee_name) {
//        Employee_name = employee_name;
//    }
//
//    public String getCourse() {
//        return Course;
//    }
//
//    public void setCourse(String course) {
//        Course = course;
//    }
//
//    public String getLinked_competency() {
//        return Linked_competency;
//    }
//
//    public void setLinked_competency(String linked_competency) {
//        Linked_competency = linked_competency;
//    }
//
//    public String getEnrollment_date() {
//        return Enrollment_date;
//    }
//
//    public void setEnrollment_date(String enrollment_date) {
//        Enrollment_date = enrollment_date;
//    }
//
//    public String getEnrollment_status() {
//        return Enrollment_status;
//    }
//
//    public void setEnrollment_status(String enrollment_status) {
//        Enrollment_status = enrollment_status;
//    }
}
