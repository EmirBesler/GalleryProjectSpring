package com.codeSlayer.galleryProject.Service;

import com.codeSlayer.galleryProject.DTO.LikeDto;
import com.codeSlayer.galleryProject.Entity.Photo;
import com.codeSlayer.galleryProject.Entity.User;
import com.codeSlayer.galleryProject.Repository.FollowRepository;
import com.codeSlayer.galleryProject.Repository.LikeRepository;
import com.codeSlayer.galleryProject.Repository.PhotoRepository;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeUserRepository;

    @Autowired
    JwtService jwtService;


    @Autowired
    PhotoRepository photoRepository;


    @Autowired
    FollowRepository followRepository;

    //token id ile user_id aynı mı?
    //user_id,photo'ya erişebilir mi?
    public ResponseEntity<PostResponseWrapper> insertLike(LikeDto likeDto, HttpServletRequest request) {
        Long tokenId = jwtService.getIdFromToken(request);
        Long userId = likeDto.getUserId();
        Long photoId = likeDto.getPhotoId();
        PostResponseWrapper postResponseWrapper = new PostResponseWrapper();
        if(tokenId.equals(userId)){
            //id'ler aynı,devam.
            Photo photo = photoRepository.getByPhotoId(photoId);
            User photoWhoBelongs = photo.getUser();
            boolean isForSave = photo.isForSave();
            //bu kişinin bu fotoğrafı like'layabilmesi için şartlar.
            //1-) fotoğraf isForSave olmayacak(gallery için olacak)eğer ok ise.
            //2-) ya fotoğrafın sahibinin profil açık olmalı,ya da userId,photoBelongs id'
            // sini follows'luyor olmalı,ya da photoBelongs ile userId aynı olmalı!
            if(!isForSave){
                if(photoWhoBelongs.isProfileOpen() || followRepository.checkTokenFollowsPathId(userId,photoWhoBelongs.getId())!=0 || userId.equals(photoWhoBelongs.getId())){
                    if(likeUserRepository.insertLike(photoId, userId)!=0){
                        postResponseWrapper.setResponse("like inserted successfully");
                        return ResponseEntity.ok(postResponseWrapper);
                    }
                    postResponseWrapper.setResponse("like inserted successfully");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(postResponseWrapper);
                }
                postResponseWrapper.setResponse("you can't like this photo");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
            }
            //kişi eğer kendisiyse de beğenemez!
            postResponseWrapper.setResponse("this photo is for save!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
        }
        postResponseWrapper.setResponse("id's must be equal!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
    }



    public String deleteLikeById(Long id){
        if(likeUserRepository.deleteLikeById(id)!=0){
            return "like deleted successfully";
        }
        return "like failed to delete";
    }
}
