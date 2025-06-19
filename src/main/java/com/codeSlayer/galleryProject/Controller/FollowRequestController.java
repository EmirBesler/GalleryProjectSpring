package com.codeSlayer.galleryProject.Controller;

import com.codeSlayer.galleryProject.DTO.FollowRequestDto;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.Service.FollowRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/followRequest")
public class FollowRequestController {


    @Autowired
    FollowRequestService followRequestService;


    //bunu test edersin!
    @PostMapping(path = "/insert")
    public ResponseEntity<PostResponseWrapper> insertFollowRequest(@RequestBody FollowRequestDto followRequestDto, HttpServletRequest request) {
        return followRequestService.insertFollowRequest(followRequestDto,request);
    }

    //silmeler en son!
    @DeleteMapping(path = "/deleteById/{id}")
    public String deleteFollowRequestById(@PathVariable Long id) {
        return followRequestService.deleteFollowRequestById(id);
    }
}
