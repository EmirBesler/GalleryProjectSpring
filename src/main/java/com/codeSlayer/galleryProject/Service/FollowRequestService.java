package com.codeSlayer.galleryProject.Service;

import com.codeSlayer.galleryProject.DTO.FollowRequestDto;
import com.codeSlayer.galleryProject.Entity.FollowRequest;
import com.codeSlayer.galleryProject.Repository.FollowRepository;
import com.codeSlayer.galleryProject.Repository.FollowRequestRepository;
import com.codeSlayer.galleryProject.Repository.UserRepository;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FollowRequestService {

    @Autowired
    JwtService jwtService;

    @Autowired
    FollowRequestRepository followRequestRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<PostResponseWrapper> insertFollowRequest(FollowRequestDto followRequestDto, HttpServletRequest request) {
        Long sendId = followRequestDto.getUserIdSend();
        Long receiveId = followRequestDto.getUserIdReceive();
        Long tokenId = jwtService.getIdFromToken(request);
        PostResponseWrapper postResponseWrapper = new PostResponseWrapper();
        //we need to check sendId and token id are equals.
        if(sendId.equals(tokenId)){
            if(receiveId.equals(tokenId)){
                postResponseWrapper.setResponse("you can't send request to yourself");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
            }
            if(followRepository.checkTokenFollowsPathId(sendId,receiveId)==0){
                //insert etmeden önce,eğer receiveId'nin profili açıksa istek hiç kaydedilmesin.
                //kişi direkt olarak followlasın.
                if(userRepository.checkProfileIsOpen(receiveId)){
                    followRepository.insertFollow(receiveId,sendId);
                    postResponseWrapper.setResponse("you start to follow " +userRepository.getUserById(receiveId).getUsername());
                    return ResponseEntity.ok(postResponseWrapper);
                }
                int x = followRequestRepository.insertFollowRequest(followRequestDto.getUserIdSend(), followRequestDto.getUserIdReceive());
                //int y = followRequestRepository.checkIsExist(SendId,ReceiveId)-->eğer istek attıysa
                //bu isteği sürekli spamlamasın!
                if(x!=0){
                    postResponseWrapper.setResponse("request send to "+userRepository.getUserById(receiveId).getUsername());
                    return ResponseEntity.ok(postResponseWrapper);
                }
                postResponseWrapper.setResponse("something went wrong");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(postResponseWrapper);
            }
            postResponseWrapper.setResponse("you already follow "+userRepository.getUserById(receiveId).getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
        }
        postResponseWrapper.setResponse("id's must be equals!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
    }

    public String deleteFollowRequestById(Long id){
        if(followRequestRepository.deleteFollowRequestById(id)!=0){
            return "follow request successfully deleted";
        }
        return "follow request failed to deleted";
    }
}
