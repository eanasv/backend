package com.test.apiTest.repository;

import com.test.apiTest.model.TrainingNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingNeedRepository extends JpaRepository<TrainingNeed, Integer> {

    @Query(value = "SELECT * FROM training_needs where employee_number=?1 AND  linked_competency =?2", nativeQuery = true)
    TrainingNeed findByEmployeeNumberAndEnrollmentStatus(String employeeNumber, String course);

    List<TrainingNeed> findAllByEmployee_EmployeeNumber(@Param("employeeNumber") Long employeeNumber);

    TrainingNeed findByLinkedCompetency(String linkedCompetency);

    @Query(value = "SELECT * FROM training_needs where employee_number=?1 AND  linkedCompetency =?2", nativeQuery = true)
    TrainingNeed findByEmployeeNumberAndLinkedCompetency(Integer employeeNumber, String linkedCompetency);
}
