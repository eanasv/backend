package com.test.apiTest.repository;

import com.test.apiTest.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {


    //    @Query(value="SELECT subcategory.id, subcategory.name, \n" +
//            "       COUNT(CASE WHEN employee.level = 1 THEN 1 END) AS level1Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 2 THEN 1 END) AS level2Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 3 THEN 1 END) AS level3Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 4 THEN 1 END) AS level4Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 5 THEN 1 END) AS level5Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 6 THEN 1 END) AS level6Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 7 THEN 1 END) AS level7Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 8 THEN 1 END) AS level8Count,\n" +
//            "       COUNT(CASE WHEN employee.level = 9 THEN 1 END) AS level9Count,\n" +
//            "       (COUNT(CASE WHEN employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9) THEN 1 END)) AS totalEmployees\n" +
//            "FROM descDashboard.subcategory\n" +
//            "LEFT JOIN descDashboard.employee ON subcategory.name = employee.sub_category\n" +
//            "WHERE subcategory.category_id = :categoryId\n" +
//            "GROUP BY subcategory.id, subcategory.name;",nativeQuery = true)
    @Query(value = "SELECT * FROM subcategory WHERE category_id =?1", nativeQuery = true)
    List<Subcategory> findByCategoryId(int categoryId);


    @Query(value = " SELECT e.entity, COUNT(*) as total_employees\n" +
            "FROM employee e\n" +
            "JOIN category c ON e.category = c.name\n" +
            "JOIN subcategory s ON e.sub_category = s.name\n" +
            "WHERE c.id = :categoryId AND s.id = :subcategoryId\n" +
            "GROUP BY e.entity;", nativeQuery = true)
    List<Object[]> findEntityEmployeeCountByCategoryId(Long categoryId, Long subcategoryId);

    //
    @Query(value = "SELECT id, name, category_id FROM subcategory WHERE category_id =:categoryId", nativeQuery = true)
    List<Subcategory> findIdAndNameCategoryId(int categoryId);

    @Query(value = "SELECT id FROM subcategory WHERE name =:subCategory", nativeQuery = true)
    Long findByName(String subCategory);


    @Query(value = "SELECT subcategory.id, subcategory.name, \n" +
            "       COUNT(CASE WHEN employee.level = 1 THEN 1 END) AS level1Count,\n" +
            "       COUNT(CASE WHEN employee.level = 2 THEN 1 END) AS level2Count,\n" +
            "       COUNT(CASE WHEN employee.level = 3 THEN 1 END) AS level3Count,\n" +
            "       COUNT(CASE WHEN employee.level = 4 THEN 1 END) AS level4Count,\n" +
            "       COUNT(CASE WHEN employee.level = 5 THEN 1 END) AS level5Count,\n" +
            "       COUNT(CASE WHEN employee.level = 6 THEN 1 END) AS level6Count,\n" +
            "       COUNT(CASE WHEN employee.level = 7 THEN 1 END) AS level7Count,\n" +
            "       COUNT(CASE WHEN employee.level = 8 THEN 1 END) AS level8Count,\n" +
            "       COUNT(CASE WHEN employee.level = 9 THEN 1 END) AS level9Count,\n" +
            "       (COUNT(CASE WHEN employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9) THEN 1 END)) AS totalSum,\n" +
            "       total.totalEmployees\n" +
            "FROM subcategory\n" +
            "LEFT JOIN employee ON LOWER(subcategory.name) = LOWER(employee.sub_category)\n" +
            "LEFT JOIN (\n" +
            "   SELECT subcategory.category_id, COUNT(*) AS totalEmployees\n" +
            "   FROM subcategory\n" +
            "   LEFT JOIN employee ON LOWER(subcategory.name) = LOWER(employee.sub_category)\n" +
            "   WHERE employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9)\n" +
            "   GROUP BY subcategory.category_id\n" +
            ") AS total ON subcategory.category_id = total.category_id\n" +
            "WHERE subcategory.category_id = :categoryId\n" +
            "GROUP BY subcategory.id, subcategory.name, total.totalEmployees;", nativeQuery = true)
    List<Object[]> findSubcategoryEmployeeCountsByLevel(Integer categoryId);


//
//    @Query(value="SELECT subcategory.id, subcategory.name,  \n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 1 THEN 1 END), 0) AS level1Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 2 THEN 1 END), 0) AS level2Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 3 THEN 1 END), 0) AS level3Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 4 THEN 1 END), 0) AS level4Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 5 THEN 1 END), 0) AS level5Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 6 THEN 1 END), 0) AS level6Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 7 THEN 1 END), 0) AS level7Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 8 THEN 1 END), 0) AS level8Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level = 9 THEN 1 END), 0) AS level9Count,\n" +
//            "       COALESCE(COUNT(CASE WHEN employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9) THEN 1 END), 0) AS totalSum\n" +
//            "FROM subcategory\n" +
//            "LEFT JOIN employee ON LOWER(subcategory.name) = LOWER(employee.sub_category) \n" +
//            "WHERE subcategory.category_id = :categoryId\n" +
//            "AND (LOWER(employee.entity) = LOWER(:entityName) OR employee.entity IS NULL) \n" +
//            "GROUP BY subcategory.id, subcategory.name;\n", nativeQuery = true)


    @Query(value = "  \n" +
            "    SELECT \n" +
            "    subcategory.id, \n" +
            "    subcategory.name, \n" +
            "    COUNT(CASE WHEN employee.level = 1 THEN 1 END) AS level1Count,\n" +
            "    COUNT(CASE WHEN employee.level = 2 THEN 1 END) AS level2Count,\n" +
            "    COUNT(CASE WHEN employee.level = 3 THEN 1 END) AS level3Count,\n" +
            "    COUNT(CASE WHEN employee.level = 4 THEN 1 END) AS level4Count,\n" +
            "    COUNT(CASE WHEN employee.level = 5 THEN 1 END) AS level5Count,\n" +
            "    COUNT(CASE WHEN employee.level = 6 THEN 1 END) AS level6Count,\n" +
            "    COUNT(CASE WHEN employee.level = 7 THEN 1 END) AS level7Count,\n" +
            "    COUNT(CASE WHEN employee.level = 8 THEN 1 END) AS level8Count,\n" +
            "    COUNT(CASE WHEN employee.level = 9 THEN 1 END) AS level9Count,\n" +
            "    (COUNT(CASE WHEN employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9) THEN 1 END)) AS totalSum,\n" +
            "    COALESCE(total.totalEmployees, 0) AS totalEmployees\n" +
            "FROM \n" +
            "    subcategory\n" +
            "LEFT JOIN \n" +
            "    employee ON LOWER(subcategory.name) = LOWER(employee.sub_category) AND LOWER(employee.entity) = LOWER(:entityName)\n" +
            "LEFT JOIN (\n" +
            "    SELECT \n" +
            "        subcategory.id, \n" +
            "        COUNT(*) AS totalEmployees\n" +
            "    FROM \n" +
            "        subcategory\n" +
            "    LEFT JOIN \n" +
            "        employee ON LOWER(subcategory.name) = LOWER(employee.sub_category) AND LOWER(employee.entity) = LOWER(:entityName)\n" +
            "    WHERE \n" +
            "        employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9)\n" +
            "    GROUP BY \n" +
            "        subcategory.id\n" +
            ") AS total \n" +
            "ON \n" +
            "    subcategory.id = total.id\n" +
            "WHERE \n" +
            "    subcategory.category_id = :categoryId\n" +
            "GROUP BY \n" +
            "    subcategory.id, subcategory.name, total.totalEmployees;", nativeQuery = true)
    List<Object[]> findSubcategoryEmployeeCountsByLevelAndEntity(Integer categoryId, String entityName);


    @Query(value = " SELECT e.entity, COUNT(*) as total_employees\n" +
            "FROM employee e\n" +
            "JOIN category c ON e.category = c.name\n" +
            "JOIN subcategory s ON e.sub_category = s.name\n" +
            "WHERE c.id = :categoryId AND s.id = :subcategoryId\n" +
            "GROUP BY e.entity;", nativeQuery = true)
    List<Object[]> findEntityEmployeeCountByCategoryIdAndSubcategoryId(Long categoryId, Long subcategoryId);


    @Query(value = " SELECT e.entity, COUNT(*) as total_employees\n" +
            "FROM employee e\n" +
            "JOIN category c ON e.category = c.name\n" +
            "JOIN subcategory s ON e.sub_category = s.name\n" +
            "WHERE c.id = :categoryId AND s.id = :subcategoryId AND e.entity =:entityName \n" +
            "GROUP BY e.entity;", nativeQuery = true)
    List<Object[]> findEntityEmployeeCountByCategoryIdAndSubcategoryIdAndEntityName(Long categoryId, Long subcategoryId, String entityName);


    @Query(value = "SELECT * FROM subcategory where id =?1;", nativeQuery = true)
    Subcategory findSubCategoryDeatilsById(Long subcategoryId);

    @Query(value = "SELECT name FROM subcategory where id =?1;", nativeQuery = true)
    String findSubCategoryNameById(Long subcategoryId);

    @Query(value = "select name from subcategory where id =:test", nativeQuery = true)
    String findTetById(Integer test);

    @Query(value = "select category_id from subcategory where id =:subcatId", nativeQuery = true)
    Integer findCatIdById(Long subcatId);
}
