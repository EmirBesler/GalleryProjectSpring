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
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into photo (path,description,is_open,is_for_gallery,is_for_save,user_id) values(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    int insertPhoto(String path, String description, boolean isOpen, boolean isForGallery, boolean isForSave, Long userId);



    @Modifying
    @Transactional
    @Query(value = "delete from photo where user_id = ?1",nativeQuery = true)
    int deleteRecordsWithUserId(Long userId);


    @Modifying
    @Transactional
    @Query(value = "delete from photo where id = ?1",nativeQuery = true)
    int deletePhotoById(Long id);



    @Query(value = "select * from photo where user_id = ?1",nativeQuery = true)
    List<Photo> getAllPhotosForProfile(Long userId);


    @Query(value = "select * from photo where is_for_gallery = true and is_for_save = false and user_id = ?1",nativeQuery = true)
    List<Photo> getAllPhotosForAnotherUsersView(Long id);


    @Query(value = "select * from photo where id = ?1",nativeQuery = true)
    Photo getByPhotoId (Long id);

}
