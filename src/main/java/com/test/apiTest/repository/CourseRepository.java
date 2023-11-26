package com.test.apiTest.repository;

import com.test.apiTest.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(value = "SELECT * FROM course where course=?1 AND training_need_id=?2", nativeQuery = true)
    Course findByCourse(String course, Long id);

    List<Course> findByTrainingNeedId(Long id);
}
