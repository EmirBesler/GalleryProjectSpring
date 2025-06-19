package com.codeSlayer.galleryProject.Controller;

import com.codeSlayer.galleryProject.Annotations.CheckIsAdmin;
import com.codeSlayer.galleryProject.DTO.FollowDto;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.Service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/follow")
public class FollowController {

    @Autowired
    FollowService followService;



    //test edersin.
    @PostMapping(path = "/insert")
    public ResponseEntity<PostResponseWrapper> insertFollow(@RequestBody FollowDto followDto, HttpServletRequest request) {
        return followService.insertFollow(followDto,request);
    }



    //silmeler en son!
    @DeleteMapping(path = "/deleteById/{id}")
    public String deleteById(@PathVariable Long id){
        return followService.deleteFollowById(id);
    }
}
