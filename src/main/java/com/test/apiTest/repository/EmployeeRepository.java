package com.test.apiTest.repository;

import com.test.apiTest.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    List<Employee> findAll();

    Employee findByEmployeeNumber(String employeeNumber);

    //@Query(value ="SELECT e.employee_number, e.entity, e.job, e.category, e.sub_category, e.role,(SELECT JSON_ARRAYAGG(JSON_OBJECT('name', s.name,'score', s.score,'achievedStatus', s.evaluation_result))FROM skill s WHERE s.employee_number = e.employee_number) AS technical_skills FROM employee e LEFT JOIN skill s ON s.employee_number = e.employee_number GROUP BY e.employee_number, e.entity, e.job, e.category, e.sub_category, e.role", nativeQuery = true)
    @Query(value = "SELECT e.employee_number, e.entity, e.job, e.category, e.sub_category, e.role,(SELECT JSON_ARRAYAGG(JSON_OBJECT('name', s.name,'score', s.score,'achievedStatus', s.evaluation_result))FROM skill s WHERE s.employee_number = e.employee_number) AS technical_skills FROM employee e", nativeQuery = true)
    List<Employee> getAllEmployeeProfDetails();


    List<Employee> findByEntity(String entityName);

    @Query("SELECT e FROM Employee e")
    @EntityGraph(value = "employee.skills")
    List<Employee> findAllWithSkills();

//    @Query(value="SELECT c.id, c.name, COUNT(e.category) as totalEmployees \n" +
//            "FROM employee e \n" +
//            "JOIN category c ON e.category = c.name \n" +
//            "GROUP BY e.category, c.id ORDER BY e.category;", nativeQuery = true)

    @Query(value = "SELECT e.category, COUNT(e.category) as count FROM employee e WHERE e.entity = ?1 GROUP BY e.category", nativeQuery = true)
    List<Object[]> findNumberOfCategoryInEntity(String entityName);


    @Query(value = "SELECT e.category, COUNT(e.category) as count, SUM(s.score) as score " +
            "FROM employee e " +
            "JOIN Skill s ON e.employee_number = s.employee_number " +
            "WHERE e.entity = ?1 " +
            "GROUP BY e.category", nativeQuery = true)
    List<Object[]> getCountNScoreInEntity(String entity);

    //SELECT e.category, SUM(s.score) / (COUNT(s.score)*3) * 100 AS percentage FROM Employee e JOIN skill s WHERE e.entity = "dewa"  AND e.employee_number=s.employee_number GROUP BY e.category;
    //   @Query(value="SELECT e.category, SUM(s.score) as sum, COUNT(s.score) as count FROM employee e JOIN skill s WHERE date = (SELECT MAX(date) from skill) AND e.entity = ?1  AND e.employee_number=s.employee_number GROUP BY e.category\n", nativeQuery = true)
//    @Query(value="SELECT e.category, COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) as achieved_count, COUNT(s.evaluation_result) as total_count \n" +
//            "FROM employee e  JOIN skill s ON e.employee_number = s.employee_number  WHERE  date = (SELECT MAX(date) from skill) AND  e.entity =?1 GROUP BY e.category", nativeQuery = true)
    @Query(value = "SELECT e.category, COUNT(CASE WHEN s.evaluation_result = 'achieved' THEN 1 END) AS achieved_count, COUNT(s.evaluation_result) AS total_count\n" +
            "FROM employee e\n" +
            "JOIN (\n" +
            "  SELECT employee_number, MAX(date) AS latest_date\n" +
            "  FROM skill\n" +
            "  GROUP BY employee_number\n" +
            ") latest_skill ON e.employee_number = latest_skill.employee_number\n" +
            "JOIN skill s ON e.employee_number = s.employee_number AND s.date = latest_skill.latest_date\n" +
            "WHERE e.entity = ?1\n" +
            "GROUP BY e.category;", nativeQuery = true)
    List<Map<String, Object>> getCategoryScoreCounts(String entity);


    @Query(value = "SELECT * FROM employee where  entity =?1 and sub_category=?2 ", nativeQuery = true)
    List<Employee> findByEntityAndSubCat(String entity, String subcat);

    @Query(value = "SELECT * FROM employee where  level =?1 and sub_category=?2 ", nativeQuery = true)
    List<Employee> findByLevelAndSubCat(String level, String subcat);


    @Query(value = "SELECT * FROM employee where  level =?1 and sub_category=?2 and entity=?3", nativeQuery = true)
    List<Employee> findByLevelAndSubcatAndEntity(String level, String subcat, String entity);


    @Query(value = "SELECT * FROM employee where sub_category=?1 ", nativeQuery = true)
    List<Employee> findBySubCat(String subcat);

    @Query(value = "SELECT * FROM employee where sub_category=?1 AND entity=?2", nativeQuery = true)
    List<Employee> findBySubCatAndEntity(String subcat, String entity);

    @Query(value = "SELECT c.id, c.name, COUNT(e.category) as totalEmployees FROM category c " +
            "LEFT JOIN employee e ON e.category = c.name  GROUP BY c.id, c.name ORDER BY c.name;", nativeQuery = true)
    List<Object[]> findNumberOfEmployeesInEachCategory();

    @Query(value = "SELECT c.id, c.name, COUNT(e.category) as totalEmployees\n" +
            "FROM category c\n" +
            "LEFT JOIN employee e ON e.category = c.name\n" +
            "WHERE e.entity = ?1\n" +
            "GROUP BY c.id, c.name\n" +
            "ORDER BY c.name;", nativeQuery = true)
    List<Object[]> findNumberOfEmployeesInEachCategoryForEntity(String entityName);

    @Query(value = "SELECT entity from employee WHERE employee_number=:employee_number",nativeQuery = true)
    String getEntityNameByEmployeeNumber(Integer employee_number);

}
