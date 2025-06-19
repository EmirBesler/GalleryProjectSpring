package com.codeSlayer.galleryProject.Repository;

import com.codeSlayer.galleryProject.Entity.Like;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into user_like (photo_id,user_id) values (?1,?2)",nativeQuery = true)
    int insertLike(Long photoId,Long userId);


    @Modifying
    @Transactional
    @Query(value = "delete from user_like where user_id = ?1 ",nativeQuery = true)
    int deleteRecordsWithUserId(Long userId);




    @Modifying
    @Transactional
    @Query(value = "delete  from  user_like where photo_id in (select id from photo where user_id = ?1)",nativeQuery = true)
    int deleteRecordsWhichHasPhotosThatHasThisUserId(Long userId);



    @Modifying
    @Transactional
    @Query(value = "delete from user_like where photo_id = ?1",nativeQuery = true)
    int deleteRecordWhichHasPhotoId(Long id);



    @Modifying
    @Transactional
    @Query(value = "delete from user_like where id = ?1",nativeQuery = true)
    int deleteLikeById(Long id);



    @Query(value = "select user_id from user_like where photo_id = ?1",nativeQuery = true)
    List<Long> getUserIdsWhoLiked(Long id);
}
