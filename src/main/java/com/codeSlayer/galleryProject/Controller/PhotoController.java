package com.codeSlayer.galleryProject.Controller;
import com.codeSlayer.galleryProject.DTO.PhotoDto;
import com.codeSlayer.galleryProject.Response.GetResponseWrapper;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.Service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;


    //test edersin
    @PostMapping(path = "/insert")
    public ResponseEntity<PostResponseWrapper> insertPhoto(@RequestBody PhotoDto photoDto,HttpServletRequest request) {
        return photoService.insertPhoto(photoDto,request);
    }


    @DeleteMapping(path = "/deleteById/{id}")
    public String deletePhotoById(@PathVariable Long id) {
        return photoService.deletePhotoById(id);
    }


    //test edersin
    @GetMapping(path = "/getUsersWhoLikedIt/{id}")
    public ResponseEntity<GetResponseWrapper<List<Long>>> userIdsWhoLikedThisPhoto(@PathVariable Long id, HttpServletRequest request) {
        return photoService.getUsersWhoLiked(id,request);
    }

}
