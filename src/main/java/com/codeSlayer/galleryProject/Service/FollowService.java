package com.codeSlayer.galleryProject.Service;

import com.codeSlayer.galleryProject.DTO.FollowDto;
import com.codeSlayer.galleryProject.Repository.FollowRepository;
import com.codeSlayer.galleryProject.Repository.FollowRequestRepository;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    FollowRequestRepository followRequestRepository;


    public ResponseEntity<PostResponseWrapper> insertFollow(FollowDto followDto, HttpServletRequest request) {
        Long whoFollowing = followDto.getUserIdFollowing();
        Long whoFollowed = followDto.getUserIdFollowed();
        Long tokenId = jwtService.getIdFromToken(request);
        PostResponseWrapper postResponseWrapper = new PostResponseWrapper();
        if(tokenId.equals(whoFollowing)) {
            //id'ler eşit,sıkıntı yok,şimdi following ile followed arasında
            //aynı şekilde bir request var mı ona bakacağız.varsa insert tamamlandıktan
            //sonra o isteği silmeyi de unutmayacağız.
            if(followRequestRepository.checkThatRecordExists(whoFollowing,whoFollowed)!=0){
                //tamam böyle bir istek var,artık insert edilebilir.
                if(followRepository.insertFollow(whoFollowed,whoFollowing)!=0){
                    //insert başarılı oldu.şimdi request'i sileceğiz.
                    followRequestRepository.deleteFollowRequestByBothId(whoFollowed,whoFollowing);
                    //kayıtı da sildik.
                    postResponseWrapper.setResponse("inert follow successful");
                    return ResponseEntity.ok(postResponseWrapper);
                }
                postResponseWrapper.setResponse("you have to send request first(even user's profile open)!");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
            }

        }
        postResponseWrapper.setResponse("token's id and the id in the RequestBody must be same!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);

    }



    public String deleteFollowById(Long id){
        if(followRepository.deleteFollowById(id) != 0){
            return "follow successfully deleted";
        }
        return "follow failed to delete";
    }

}
