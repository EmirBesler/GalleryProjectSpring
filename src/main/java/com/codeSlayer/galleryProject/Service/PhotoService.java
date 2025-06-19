package com.codeSlayer.galleryProject.Service;

import com.codeSlayer.galleryProject.DTO.PhotoDto;
import com.codeSlayer.galleryProject.Entity.Photo;
import com.codeSlayer.galleryProject.Repository.FollowRepository;
import com.codeSlayer.galleryProject.Repository.LikeRepository;
import com.codeSlayer.galleryProject.Repository.PhotoRepository;
import com.codeSlayer.galleryProject.Repository.UserRepository;
import com.codeSlayer.galleryProject.Response.GetResponseWrapper;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    private LikeRepository likeRepository;


    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;


    //token's id and photo's userid must be equals
    //photo's (isForSave ^ isForGallery) == true olmalı(XOR)
    public ResponseEntity<PostResponseWrapper> insertPhoto(PhotoDto photoDto,HttpServletRequest request) {
        PostResponseWrapper postResponseWrapper = new PostResponseWrapper();
        if(jwtService.getIdFromToken(request).equals(photoDto.getUserId())){
            if(photoDto.isForGallery()^photoDto.isForSave()){
                int x = photoRepository.insertPhoto(photoDto.getPath(),photoDto.getDescription(),photoDto.isOpen(),photoDto.isForGallery(),photoDto.isForSave(),photoDto.getUserId());
                if(x != 0){
                    postResponseWrapper.setResponse("photo inserted successfully");
                    return ResponseEntity.ok(postResponseWrapper);
                }
                postResponseWrapper.setResponse("something went wrong");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(postResponseWrapper);
            }
            postResponseWrapper.setResponse("you must choose a gallery or save,not both");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
        }

        postResponseWrapper.setResponse("id's must be equal!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
    }



    public String deletePhotoById(Long photoId) {
        String answer = "";
        //step1->delete like records which has this photoId
        if(likeRepository.deleteRecordWhichHasPhotoId(photoId)!=0){
            answer = answer + "likes succesfully deleted";
        }
        else{
            answer = answer + "like deleting failed";
        }
        //final step,delete photo
        if(photoRepository.deletePhotoById(photoId)!=0){
            answer = answer + "photo deleted successfully";
        }
        else{
            answer = answer + "photo deleting failed";
        }
        return answer;
    }

    //buradaki id photo'nun id'si.
    public ResponseEntity<GetResponseWrapper<List<Long>>> getUsersWhoLiked(Long id, HttpServletRequest request) {
        System.out.println("çalıştı");
        Long tokenId = jwtService.getIdFromToken(request);
        Photo photo = photoRepository.getByPhotoId(id);
        GetResponseWrapper<List<Long>> response = new GetResponseWrapper<>();
        //tokenId,photo'ya ulaşabilir mi?
        if(photo!=null){
            if(!photo.isForSave() || tokenId.equals(photo.getUser().getId())){
                if(userRepository.checkProfileIsOpen(photo.getUser().getId())|| followRepository.checkTokenFollowsPathId(tokenId,photo.getUser().getId())!=0 || tokenId.equals(photo.getUser().getId())){
                    response.setResponse(likeRepository.getUserIdsWhoLiked(id));
                    response.setMessage("successful");
                    return ResponseEntity.ok(response);
                }
                response.setResponse(null);
                response.setMessage("you can't see that picture");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            response.setResponse(null);
            response.setMessage("this photo is for save!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        response.setResponse(null);
        response.setMessage("no photo has id: "+id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
