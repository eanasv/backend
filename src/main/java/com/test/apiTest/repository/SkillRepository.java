package com.test.apiTest.repository;

import com.test.apiTest.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository

public interface SkillRepository extends JpaRepository<Skill, Integer> {


    //    @Query(value="SELECT * from skill WHERE date = (SELECT MAX(date) from skill) AND employee_number=?1", nativeQuery = true)
    @Query(value = "SELECT s.*\n" +
            "FROM skill AS s\n" +
            "JOIN employee AS e ON s.employee_number = e.employee_number\n" +
            "WHERE e.employee_number = ?1\n" +
            "  AND s.date = (\n" +
            "    SELECT MAX(date)\n" +
            "    FROM skill\n" +
            "    WHERE employee_number = e.employee_number\n" +
            "  )\n", nativeQuery = true)
    List<Skill> findByEmployeeNumber(String employeeNumber);

    @Query(value = "SELECT * from skill where name=?1 and employee_number=?2 ", nativeQuery = true)
    Skill findByNameAndEmployeeNumber(String skillName, Integer employeeNumber);

    @Query(value = "SELECT * from skill where name=?1 and date =?2 and employee_number=?3 ", nativeQuery = true)
    Skill findByNameAndDateAndEmployeeNumber(String skillName, String date, String reservedEmployeeNumber);


    @Query(value = "SELECT e.entity, DATE_FORMAT(s.date, '%m/%Y') AS month, ROUND(CAST(AVG(s.score) AS DOUBLE),1) AS average_score\n" +
            "FROM employee e\n" +
            "INNER JOIN skill s ON e.employee_number = s.employee_number\n" +
            "WHERE s.date BETWEEN :startDate AND :endDate \n" +
            "GROUP BY e.entity, month\n" +
            "ORDER BY e.entity, month", nativeQuery = true)
    List<Object[]> findMonthlyAveragesByCompany(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT YEAR(s.date) AS year, \n" +
            "ROUND((COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) / COUNT(*)) * 100, 0) AS score\n" +
            "FROM employee e\n" +
            "INNER JOIN skill s ON e.employee_number = s.employee_number\n" +
            "WHERE e.entity IN :entityNames \n" +
            "GROUP BY year\n" +
            "ORDER BY year;", nativeQuery = true)
    List<Object[]> getImprovementSkillRanksByEntityOverYears(@Param("entityNames") List<String> entityNames);

    //calculate mnthly avarage of one company
//    @Query(value = "SELECT e.entity, DATE_FORMAT(s.date, '%b %Y') AS month, ROUND((COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) / COUNT(*)) * 100, 0) AS monthly_score\n" +
//            "FROM employee e\n" +
//            "INNER JOIN skill s ON e.employee_number = s.employee_number\n" +
//            "WHERE s.date BETWEEN :startDate AND :endDate AND e.entity = :entityName \n" +
//            "GROUP BY e.entity, month\n" +
//            "ORDER BY e.entity, month",nativeQuery = true)
//    List<Object[]> findMonthlyAveragesForOneCompany(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("entityName") String entityName);

    @Query(value = "SELECT e.entity, DATE_FORMAT(s.date, '%b %Y') AS month, ROUND((COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) / COUNT(*)) * 100, 0) AS monthly_score\n" +
            "FROM employee e\n" +
            "INNER JOIN skill s ON e.employee_number = s.employee_number\n" +
            "WHERE s.date BETWEEN :startDate AND :endDate AND e.entity = :entityName \n" +
            "GROUP BY e.entity, month\n" +
            "ORDER BY e.entity, month", nativeQuery = true)
    List<Object[]> findMonthlyAveragesForOneCompany(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("entityName") String entityName);


    @Query(value = "SELECT e.entity, DATE_FORMAT(s.date, '%b %Y') AS month, \n" +
            "ROUND((COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) / COUNT(*)) * 100, 0) AS monthly_score\n" +
            "FROM employee e\n" +
            "INNER JOIN skill s ON e.employee_number = s.employee_number\n" +
            "WHERE s.date BETWEEN :startDate AND :endDate AND e.entity IN :entityNames \n" +
            "GROUP BY e.entity, month\n" +
            "ORDER BY monthly_score DESC", nativeQuery = true)
    List<Object[]> findMonthlyAveragesForArrayOfEntities(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("entityNames") List<String> entityNames);


    //    @Query(value="SELECT e.entity, DATE_FORMAT(s.date, '%b %Y') AS month, ROUND((COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) / COUNT(*)) * 100, 0) AS latest_monthly_score FROM employee e\n" +
//            "INNER JOIN skill s ON e.employee_number = s.employee_number\n" +
//            "WHERE date = (SELECT MAX(date) from skill) AND e.entity IN :entityNames \n" +
//            "GROUP BY e.entity, month\n" +
//            "ORDER BY latest_monthly_score DESC;\n", nativeQuery = true)
    @Query(value = "SELECT e.entity, DATE_FORMAT(MAX(s.date), '%b %Y') AS month, " +
            "ROUND((COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) / COUNT(*)) * 100, 0) AS latest_monthly_score " +
            "FROM employee e " +
            "INNER JOIN skill s ON e.employee_number = s.employee_number " +
            "WHERE e.entity IN :entityNames " +
            "GROUP BY e.entity " +
            "ORDER BY latest_monthly_score DESC", nativeQuery = true)
    List<Object[]> findLatestMonthlyAveragesForArrayOfCompanies(@Param("entityNames") List<String> entityNames);

    //    @Query(value="SELECT ts.skill_name FROM technical_skills AS ts JOIN role AS r ON ts.role_id = r.id WHERE r.name = :name;",nativeQuery = true)
//    List<String> findTechnicalSkillNamesByRoleNames(String name);
    @Query(value = "SELECT ts.skill_name FROM technical_skills AS ts JOIN role AS r ON ts.role_id = r.id WHERE r.name = :name", nativeQuery = true)
    List<String> findTechnicalSkillNamesByRoleNames(String name);

@Query(value = "SELECT COUNT(*) from skill where employee_number=:employee_number", nativeQuery = true)
    Integer findCountByEmployeeNumber(String employee_number);
}
