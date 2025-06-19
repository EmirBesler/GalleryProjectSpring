package com.codeSlayer.galleryProject.Controller;

import com.codeSlayer.galleryProject.Annotations.CheckIsAdmin;
import com.codeSlayer.galleryProject.DTO.LikeDto;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.Service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/like")
public class LikeController {

    @Autowired
    LikeService likeService;



    //test edersin
    @PostMapping(path = "/insert")
    public ResponseEntity<PostResponseWrapper> insertLike(@RequestBody LikeDto likeDto, HttpServletRequest request) {
        return likeService.insertLike(likeDto,request);
    }




    //silmeler en son!
    @DeleteMapping(path = "/deleteById/{id}")
    public String deleteLikeById(@PathVariable Long id) {
        return likeService.deleteLikeById(id);
    }
}
