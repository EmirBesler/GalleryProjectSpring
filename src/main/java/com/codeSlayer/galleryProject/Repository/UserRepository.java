package com.codeSlayer.galleryProject.Repository;

import com.codeSlayer.galleryProject.Entity.Photo;
import com.codeSlayer.galleryProject.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where username = ?1",nativeQuery = true)
    User findUserByUsername(String username);


    @Modifying
    @Transactional
    @Query(value = "insert into user(username,password,email,is_profile_open" +
            ",profile_description,profile_picture_path) values(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    int insertUser(String username, String password,String mail,boolean isProfileOpen
            ,String profileDescription,String profilePicturePath);


    @Query(value = "select id from user where username = ?1",nativeQuery = true)
    Long getIdByUsername(String username);


    @Query(value = "select * from user where id = ?1",nativeQuery = true)
    User getUserById(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from user where id = ?1",nativeQuery = true)
    int deleteUserById(Long id);



    @Query(value = "select email from user where username = ?1",nativeQuery = true)
    String getEmailByUsername(String username);


    @Query(value = "select * from user",nativeQuery = true)
    List<User> getAllUsers();


    @Query(value = "select is_profile_open from user where id = ?1",nativeQuery = true)
    boolean checkProfileIsOpen(Long id);


}
