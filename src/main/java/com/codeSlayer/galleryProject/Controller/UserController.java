package com.codeSlayer.galleryProject.Controller;

import com.codeSlayer.galleryProject.Annotations.CheckId;
import com.codeSlayer.galleryProject.Annotations.CheckIsAdmin;
import com.codeSlayer.galleryProject.DTO.UserDto;
import com.codeSlayer.galleryProject.Entity.FollowRequest;
import com.codeSlayer.galleryProject.Entity.Photo;
import com.codeSlayer.galleryProject.Entity.User;
import com.codeSlayer.galleryProject.Repository.UserRepository;
import com.codeSlayer.galleryProject.Response.GetResponseWrapper;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.Response.ProfileResponseWrapper;
import com.codeSlayer.galleryProject.Service.UserService;
import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;


    @PostMapping(path = "/register")
    public ResponseEntity<PostResponseWrapper> register(@RequestBody User user) {
        return userService.userRegister(user);
    }


    @PostMapping(path = "/authenticate")
    public ResponseEntity<GetResponseWrapper<String>> authUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        return userService.authUser(userDto,request);
    }


    //delete'ler sonra
    @DeleteMapping (path = "/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id, HttpServletRequest request) {
        return userService.deleteByIdStart(id);
    }


    @CheckId
    @GetMapping(path = "/getbyId/{id}")
    public ResponseEntity<GetResponseWrapper<User>> getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return userService.getUserById(id);
    }



    @CheckIsAdmin
    @GetMapping("/getAllUsers")
    public ResponseEntity<GetResponseWrapper<List<User>>> getAllUsers(HttpServletRequest request) {
        return userService.getAllUsers();
    }



    //burada photo değil sadece path ve açıklama dönsek sanki daha iyi olur?
    @GetMapping("/getAllPhotosForProfile/{id}")
    public ResponseEntity<GetResponseWrapper<List<Photo>>> getAllPhotosForGallery(@PathVariable Long id, HttpServletRequest request) {
        return userService.getAllPhotosById(id,request);
    }


    @CheckId
    @GetMapping(path = "/getAllFollowRequestById/{id}")
    public List<Long> getAllUserIdsWhoSendFollowRequestToThisUserId(@PathVariable Long id, HttpServletRequest request) {
        return userService.getAllUserIdsWhoSendFollowRequestToThisUserId(id);
    }


    //user'ın takip'lerini gösteriyor.
    @GetMapping(path = "/getFollowsById/{id}")
    public ResponseEntity<GetResponseWrapper<List<Long>>> getAllUserIdsWhichThisUserFollows(@PathVariable Long id, HttpServletRequest request) {
        return userService.getFollowsById(id,request);
    }


    //user'ın takipçilerini gösteriyor
    @GetMapping(path = "/getFollowersById/{id}")
    public ResponseEntity<GetResponseWrapper<List<Long>>> getAllFollowersThisUserHas(@PathVariable Long id, HttpServletRequest request) {
        return userService.getFollowersById(id,request);
    }


    //profil görüntüleme----------------------------------------------------------------------------------------------
    @GetMapping(path = "/seeProfile/{id}")
    public ResponseEntity<ProfileResponseWrapper> SeeProfile(@PathVariable Long id, HttpServletRequest request) {
        return userService.getProfileByUserId(id,request);
    }
    //profil görüntüleme----------------------------------------------------------------------------------------------

    @GetMapping(path = "/getIdFromToken")
    public ResponseEntity<PostResponseWrapper> getIdFromToken(HttpServletRequest request) {
        PostResponseWrapper postResponseWrapper = new PostResponseWrapper();
        postResponseWrapper.setResponse(jwtService.getIdFromToken(request).toString());
        return ResponseEntity.ok().body(postResponseWrapper);
    }




    @GetMapping(path = "/getImage")
    public ResponseEntity<Resource> getPictureByPath(@RequestParam String path) throws MalformedURLException {
        Path filePath = Paths.get(path);
        Resource file = new UrlResource(filePath.toUri());

        MediaType mediaType = MediaTypeFactory
                .getMediaType(file)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity
                .ok()
                .contentType(mediaType) // bu header'ı düzgün ekle
                .body(file); // bu önemli: mediaType değil, dosyanın kendisi
    }

}
