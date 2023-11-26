package com.test.apiTest.dto;

import java.util.List;

public class TrainingNeedDTO {

//    private String name;
//    private String enrollmentDate;
//    private String enrollmentStatus;
//    private String course;

    private String linkedCompetency;

    private List<CourseDTO> courses;

    public TrainingNeedDTO(String linkedCompetency, List<CourseDTO> courses) {
        this.linkedCompetency = linkedCompetency;
        this.courses = courses;
    }

    public TrainingNeedDTO() {

    }

    public String getLinkedCompetency() {
        return linkedCompetency;
    }

    public void setLinkedCompetency(String linkedCompetency) {
        this.linkedCompetency = linkedCompetency;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }


    //    public String getCourse() {
//        return course;
//    }
//
//    public void setCourse(String course) {
//        this.course = course;
//    }


    // Getters and setters

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEnrollmentDate() {
//        return enrollmentDate;
//    }
//
//    public void setEnrollmentDate(String enrollmentDate) {
//        this.enrollmentDate = enrollmentDate;
//    }
//
//    public String getEnrollmentStatus() {
//        return enrollmentStatus;
//    }
//
//    public void setEnrollmentStatus(String enrollmentStatus) {
//        this.enrollmentStatus = enrollmentStatus;
//    }
}
