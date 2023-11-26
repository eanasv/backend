package com.test.apiTest.repository;

import com.test.apiTest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();

    @Query(value = "SELECT id FROM category WHERE name =?1", nativeQuery = true)
    Integer findByName(String category);

    @Query(value = "SELECT  category.name as categoryName, subcategory.name as subcategoryName,\n" +
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
            "INNER JOIN category ON category.id = subcategory.category_id\n" +
            "LEFT JOIN employee ON LOWER(subcategory.name) = LOWER(employee.sub_category)\n" +
            "LEFT JOIN (\n" +
            "   SELECT subcategory.category_id, COUNT(*) AS totalEmployees\n" +
            "   FROM subcategory\n" +
            "   LEFT JOIN employee ON LOWER(subcategory.name) = LOWER(employee.sub_category)\n" +
            "   WHERE employee.level IN (1, 2, 3, 4, 5, 6, 7, 8, 9)\n" +
            "   GROUP BY subcategory.category_id\n" +
            ") AS total ON subcategory.category_id = total.category_id\n" +
            "GROUP BY category.id, subcategory.id, category.name, subcategory.name, total.totalEmployees;", nativeQuery = true)
    List<Object[]> findAllCategorySubcategoryEmployeeCountsByLevel(long id);


    @Query(value = "select name from category where id=?1", nativeQuery = true)
    String findNameById(Integer categoryId);

    @Query(value = "select name from category where id=?1", nativeQuery = true)
    String findByRoleName(Integer categoryId);


}
