package com.test.apiTest.repository;

import com.test.apiTest.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);


    Optional<UserInfo> findByName(String email);

//    @Query(value="select * from user_info where username=?1 AND password=?2 AND entity=?3 AND role=?4",nativeQuery = true)
//    UserInfo loginCheck(String name, String password, String entity, String role);

    @Query(value = "select * from user_info where username=?1 AND password=?2", nativeQuery = true)
    UserInfo loginCheck(String name, String password);

    @Query(value = "Select * from user_info ", nativeQuery = true)
    List<UserInfo> findAllUser();

    // void delete(UserInfoDTO user);
    @Query(value = "select * from user_info where username=?1", nativeQuery = true)
    Optional<UserInfo> findByUsername(String username);

    @Query(value = "select * from user_info where id=?1", nativeQuery = true)
    Optional<UserInfo> findById(Long id);
}
