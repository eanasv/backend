package com.test.apiTest.repository;

import com.test.apiTest.model.SoftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftSkillRepository extends JpaRepository<SoftSkill, Long> {

    @Query(value = "SELECT ss.skill_name FROM soft_skills AS ss JOIN role AS r ON ss.role_id = r.id WHERE r.name = :name", nativeQuery = true)
    List<String> findSoftSkillNamesByRoleNames(String name);

    @Query(value = "SELECT * FROM soft_skills where role_id=:id", nativeQuery = true)
    List<SoftSkill> findByRoleId(Integer id);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM soft_skills t WHERE t.skill_name = :name", nativeQuery = true)
    Integer existsByName(String name);
}
