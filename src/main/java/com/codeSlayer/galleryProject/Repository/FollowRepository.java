package com.codeSlayer.galleryProject.Repository;

import com.codeSlayer.galleryProject.Entity.Follow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {


    @Modifying
    @Transactional
    @Query(value = "insert into follow (user_id_ed_id,user_id_ing_id) values(?1,?2)",nativeQuery = true)
    int insertFollow(Long userIdFollowed, Long userIdFollowing);


    @Modifying
    @Transactional
    @Query(value = "delete from follow where user_id_ed_id = ?1 or user_id_ing_id = ?1",nativeQuery = true)
    int deleteRecordsWithEdOrIng(Long id);



    @Modifying
    @Transactional
    @Query(value = "delete from follow where id = ?1",nativeQuery = true)
    int deleteFollowById(Long id);




    //ing follows ed,that simple.
    @Query(value = "select user_id_ed_id from follow where user_id_ing_id = ?1",nativeQuery = true)
    List<Long> getFollowsByUserId(Long userId);

    @Query(value = "select user_id_ing_id from follow where user_id_ed_id = ?1",nativeQuery = true)
    List<Long> getFollowersByUserId(Long userId);


    @Query(value = "select count(*) from follow where user_id_ing_id = ?1 and user_id_ed_id = ?2",nativeQuery = true)
    int checkTokenFollowsPathId(Long tokenId,Long pathId);
}
