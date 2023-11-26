package com.test.apiTest.repository;

import com.test.apiTest.model.EmployeeEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeEvaluationRepo extends JpaRepository<EmployeeEvaluation, Integer> {
}
