package com.test.apiTest.repository;

import com.test.apiTest.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //    @Query(value="SELECT * FROM role where subcategory_id=?1", nativeQuery = true)// this will work only for the subcategories which dont have divisions
    @Query(value = " SELECT r.* FROM role r JOIN subcategory s ON r.subcategory_id = s.id\n" +
            "LEFT JOIN division d ON r.division_id = d.division_id WHERE s.id = ?1\n" +
            "  AND (d.division_id IS NULL OR d.division_id = (\n" +
            "    SELECT division_id\n" +
            "    FROM role\n" +
            "    WHERE name = ?2\n" +
            "    LIMIT 1\n" +
            "  ));", nativeQuery = true)
    List<Role> findBySubcategoryId(Long subcategoryId, String roleName);

//    @Query(value = "SELECT subcategory_id FROM role WHERE name = :name;", nativeQuery = true)
//    Integer findSubcategoryIdByRoleName( String name);

    @Query(value = "SELECT subcategory_id FROM role WHERE name = ?1", nativeQuery = true)
    Integer findSubcategoryIdByRoleName(@Param("name") String name);

    @Query(value = "SELECT level FROM role where name =?1", nativeQuery = true)
    Integer findLevelByRoleName(String roleName);

    @Query(value = "SELECT * FROM role WHERE name = ?1", nativeQuery = true)
    Role findSubcategoryIdByRoleName1(String name);

    @Query(value = "SELECT name FROM role ", nativeQuery = true)
    List<String> findAllRoleName();
}
