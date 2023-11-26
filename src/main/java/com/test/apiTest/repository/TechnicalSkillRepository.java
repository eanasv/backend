package com.test.apiTest.repository;

import com.test.apiTest.model.TechnicalSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalSkillRepository extends JpaRepository<TechnicalSkill, Long> {

    @Query(value = "SELECT * FROM technical_skills where role_id=:id", nativeQuery = true)
    List<TechnicalSkill> findByRoleId(Integer id);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM technical_skills t WHERE t.skill_name = :name", nativeQuery = true)
    Integer existsByName(String name);
}
