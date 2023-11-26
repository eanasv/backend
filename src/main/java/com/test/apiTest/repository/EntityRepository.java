package com.test.apiTest.repository;

import com.test.apiTest.model.Entities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntityRepository extends JpaRepository<Entities, Long> {

    @Query(value = "SELECT  *  FROM entity m", nativeQuery = true)
    List<Entities> findAllEntities();

    @Query(value = "SELECT * FROM entity e JOIN employee em WHERE e.name = em.entity ", nativeQuery = true)
    List<Entities> findAllWithEmployees();

    @Query(value = "SELECT  *  FROM entity m WHERE m.label=?1", nativeQuery = true)
    Entities findByLabel(String entityName);


    //    @Query(value="SELECT e.category, SUM(s.score) as sum, COUNT(s.score) as count FROM MyStore.Employee e JOIN MyStore.skill s WHERE e.entity = ?1  AND e.employee_number=s.employee_number GROUP BY e.category\n", nativeQuery = true)
}
