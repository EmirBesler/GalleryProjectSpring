package com.codeSlayer.galleryProject.Repository;

import com.codeSlayer.galleryProject.Entity.FollowRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {


    @Modifying
    @Transactional
    @Query(value = "insert into follow_request (user_send_id,user_receive_id) values(?1,?2)",nativeQuery = true)
    int insertFollowRequest(Long userIdSend,Long userIdReceive);



    @Modifying
    @Transactional
    @Query(value = "delete from follow_request where user_receive_id = ?1 or user_send_id = ?1",nativeQuery = true)
    int deleteRecordsWithFollowerOrFollowed(Long id);



    @Modifying
    @Transactional
    @Query(value = "delete from follow_request where id = ?1",nativeQuery = true)
    int deleteFollowRequestById(Long id);


    @Query(value = "select user_send_id from follow_request where user_receive_id = ?1",nativeQuery = true)
    List<Long> getAllSendIdByReceiveId(Long userId);



    @Query(value = "select count(*) from follow_request where user_receive_id = ?2 and user_send_id = ?1",nativeQuery = true)
    int checkThatRecordExists(Long followingId,Long followedId);



    @Modifying
    @Transactional
    @Query(value = "delete from follow_request where user_send_id = ?2 and user_receive_id = ?1 ",nativeQuery = true)
    int deleteFollowRequestByBothId(Long followedId,Long followingId);
}
