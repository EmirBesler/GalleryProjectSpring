package com.codeSlayer.galleryProject.Service;

import com.codeSlayer.galleryProject.DTO.PhotoDto;
import com.codeSlayer.galleryProject.DTO.PhotoDtoForProfile;
import com.codeSlayer.galleryProject.DTO.UserDto;
import com.codeSlayer.galleryProject.DTO.UserIconDto;
import com.codeSlayer.galleryProject.Entity.Follow;
import com.codeSlayer.galleryProject.Entity.FollowRequest;
import com.codeSlayer.galleryProject.Entity.Photo;
import com.codeSlayer.galleryProject.Entity.User;
import com.codeSlayer.galleryProject.Repository.*;
import com.codeSlayer.galleryProject.Response.GetResponseWrapper;
import com.codeSlayer.galleryProject.Response.PostResponseWrapper;
import com.codeSlayer.galleryProject.Response.ProfileResponseWrapper;
import com.codeSlayer.galleryProject.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowRequestRepository followRequestRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PhotoRepository photoRepository;


    public ResponseEntity<PostResponseWrapper> userRegister(User user) {
        PostResponseWrapper postResponseWrapper = new PostResponseWrapper();
        String checkFirst = checkRegisterInfoValid(user);
        if(checkFirst!=null) {
            postResponseWrapper.setResponse(checkFirst);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
        }
        int control = userRepository.insertUser(user.getUsername()
                ,bCryptPasswordEncoder.encode(user.getPassword()),user.getEmail(),user.isProfileOpen()
                ,user.getProfileDescription(),user.getProfilePicturePath());
        if(control!=0){
            postResponseWrapper.setResponse("user successfully registered");
            return ResponseEntity.ok().body(postResponseWrapper);
        }
        postResponseWrapper.setResponse("something went wrong");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(postResponseWrapper);
    }

    public ResponseEntity<GetResponseWrapper<String>> authUser(UserDto userDto, HttpServletRequest request) {
        GetResponseWrapper<String> getResponseWrapper = new GetResponseWrapper<>();
        String role;
        if(userDto.getUsername().equals("admin")){
            role = "ADMIN";
        }
        else{
            role = "USER";
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        authenticationProvider.authenticate(auth);
        getResponseWrapper.setResponse(jwtService.generateToken(userDto,request,role));
        return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
    }


    public ResponseEntity<GetResponseWrapper<User>> getUserById(Long id){
        GetResponseWrapper<User> responseWrapper = new GetResponseWrapper<>();
        User user = userRepository.getUserById(id);
        if(user!=null){
            responseWrapper.setResponse(user);
            responseWrapper.setMessage("successful");
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        responseWrapper.setResponse(null);
        responseWrapper.setMessage("something went wrong");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);

    }



    //diğer repo metotları sırf bu silme işi için oluşturuldu.
    //onları sakın açma!
    public String deleteByIdStart(Long id) {
        //step1->delete follow and request,
        String answer = "";
        if(followRepository.deleteRecordsWithEdOrIng(id)*followRequestRepository.deleteRecordsWithFollowerOrFollowed(id)!=0){
            answer = answer+ "requests and follows deleted successfully, ";
        }
        else{
            answer = answer+ "deleting requests and follows failed, ";
        }
        //step2->delete like
        if(likeRepository.deleteRecordsWithUserId(id)!=0){
            answer = answer+ "likes deleting  step 1 successful ";
        }
        else{
            answer = answer+ "deleting likes step 1 failed, ";
        }
        //step3->delete like(there is might be a photo's using this user,and we might
        //use that photos!
        if(likeRepository.deleteRecordsWhichHasPhotosThatHasThisUserId(id)!=0){
            answer = answer+ "likes deleting step 2 successful, ";
        }
        else{
            answer = answer+ "deleting likes step 2 failed, ";
        }
        //step4->delete photos
        if(photoRepository.deleteRecordsWithUserId(id)!=0){
            answer = answer+ "photos deleted successfully, ";
        }
        else{
            answer = answer+ "photos deleting failed, ";
        }
        //final step,delete user off course
        if(userRepository.deleteUserById(id)!=0){
            answer = answer+ "user successfully deleted";
        }
        else{
            answer = answer+ "user failed to delete";
        }
        System.out.println(answer);
        return answer;
    }


    public String checkRegisterInfoValid(User user){
        String answer = "";
        if(userRepository.getIdByUsername(user.getUsername())!=null){
            answer = answer+"invalid username,";
        }
        if(userRepository.getEmailByUsername(user.getUsername())!=null){
            answer = answer+ "invalid email";
        }
        if(answer.isEmpty()){
            return null;
        }
        return answer;
    }


    public ResponseEntity<GetResponseWrapper<List<User>>> getAllUsers(){
        GetResponseWrapper<List<User>> responseWrapper = new GetResponseWrapper<>();
        List<User> users = userRepository.getAllUsers();
        if(users!=null){
            responseWrapper.setResponse(users);
            responseWrapper.setMessage("successful");
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        responseWrapper.setResponse(null);
        responseWrapper.setMessage("something went wrong");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
    }



    public ResponseEntity<GetResponseWrapper<List<Photo>>> getAllPhotosById(Long id,HttpServletRequest request){
        GetResponseWrapper<List<Photo>> getResponseWrapper = new GetResponseWrapper<>();
        Long tokenId = jwtService.getIdFromToken(request);
        //is user wants to see his own profile
        if(tokenId.equals(id)){
            getResponseWrapper.setMessage("wants to see his own profile");
            getResponseWrapper.setResponse(photoRepository.getAllPhotosForProfile(id));
            return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
        }
        //this user wants to see another user's profile,is this user's profile open
        else if(userRepository.checkProfileIsOpen(id)){
            getResponseWrapper.setMessage("wants to see another user's profile and profile is open(maybe follows that user too, but doesnt matter)");
            getResponseWrapper.setResponse(photoRepository.getAllPhotosForAnotherUsersView(id));
            return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
        }
        //this user want to see another user's profile,that profile is not open
        //but this user might follows him?
        else if(isTokenUserFollowsPathId(tokenId,id)){
            getResponseWrapper.setMessage("wants to see another user's profile and he follows,it's okey");
            getResponseWrapper.setResponse(photoRepository.getAllPhotosForAnotherUsersView(id));
            return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
        }
        getResponseWrapper.setMessage("wants to see another user's profile but that profile is not open and user don't follow that user");
        getResponseWrapper.setResponse(null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getResponseWrapper);

    }


    //zaten checkId yapıldı,sorun yok
    public List<Long> getAllUserIdsWhoSendFollowRequestToThisUserId(Long id){
        return followRequestRepository.getAllSendIdByReceiveId(id);
    }


    public ResponseEntity<GetResponseWrapper<List<Long>>> getFollowsById(Long id,HttpServletRequest request){
        GetResponseWrapper<List<Long>> responseWrapper = new GetResponseWrapper<>();
        if(jwtService.getIdFromToken(request).equals(id)){
            responseWrapper.setMessage("wants to see his own follows");
            responseWrapper.setResponse(followRepository.getFollowsByUserId(id));
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        if(userRepository.checkProfileIsOpen(id)){
            responseWrapper.setMessage("wants to see open user's follows");
            responseWrapper.setResponse(followRepository.getFollowsByUserId(id));
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        if(isTokenUserFollowsPathId(jwtService.getIdFromToken(request),id)){
            responseWrapper.setMessage("wants to see user's follows (follows that user)");
            responseWrapper.setResponse(followRepository.getFollowsByUserId(id));
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        responseWrapper.setMessage("this user can't see this user's follows");
        responseWrapper.setResponse(null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
    }

    public ResponseEntity<GetResponseWrapper<List<Long>>> getFollowersById(Long id,HttpServletRequest request){
        GetResponseWrapper<List<Long>> responseWrapper = new GetResponseWrapper<>();
        if(jwtService.getIdFromToken(request).equals(id)){
            responseWrapper.setMessage("wants to see his own followers");
            responseWrapper.setResponse(followRepository.getFollowersByUserId(id));
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        if(userRepository.checkProfileIsOpen(id)){
            responseWrapper.setMessage("wants to see open user's followers");
            responseWrapper.setResponse(followRepository.getFollowersByUserId(id));
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        if(isTokenUserFollowsPathId(jwtService.getIdFromToken(request),id)){
            responseWrapper.setMessage("wants to see user's followers (follows that user)");
            responseWrapper.setResponse(followRepository.getFollowersByUserId(id));
            return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
        }
        responseWrapper.setMessage("this user can't see this user's followers");
        responseWrapper.setResponse(null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
    }

    public boolean isTokenUserFollowsPathId(Long tokenId,Long pathId){
        return followRepository.checkTokenFollowsPathId(tokenId,pathId)!=0;
    }


    //NULL'ları yakalayıp yönet!
    public ResponseEntity<ProfileResponseWrapper> getProfileByUserId(Long id,HttpServletRequest request){
        String messageForResponse = "";
        ProfileResponseWrapper profileResponse = new ProfileResponseWrapper();
        //photo
        GetResponseWrapper<List<Photo>> photoResponse =getAllPhotosById(id,request).getBody();
        if(photoResponse==null){
            profileResponse.setPhotos(null);
            messageForResponse = messageForResponse+"no photo, ";
        }
        else{
            messageForResponse = messageForResponse+photoResponse.getMessage()+", ";
            List<Photo> rawPhotos = photoResponse.getResponse();
            if(rawPhotos!=null){
                List<PhotoDtoForProfile> clearPhotos = new ArrayList<>();
                for(int i = 0;i<rawPhotos.size();i++){
                    clearPhotos.add(photoToDtoConverter(rawPhotos.get(i)));
                }
                profileResponse.setPhotos(clearPhotos);
            }
            else{
                profileResponse.setPhotos(null);
            }

        }
        //username
        GetResponseWrapper<User> userResponse =getUserById(id).getBody();
        if(userResponse==null){
            profileResponse.setUsername(null);
            profileResponse.setUserDescription(null);
            profileResponse.setProfilePhotoPath(null);
            messageForResponse = messageForResponse+"no user, ";
            messageForResponse = messageForResponse+"no profile description, ";
            messageForResponse = messageForResponse+"no profile photo, ";
        }
        else{
            messageForResponse = messageForResponse+userResponse.getMessage()+", ";
            if(userResponse.getResponse()!=null){
                profileResponse.setUsername(userResponse.getResponse().getUsername());
                profileResponse.setUserDescription(userResponse.getResponse().getProfileDescription());
                profileResponse.setProfilePhotoPath(userResponse.getResponse().getProfilePicturePath());
            }
            else{
                profileResponse.setUsername(null);
                profileResponse.setUserDescription(null);
                profileResponse.setProfilePhotoPath(null);
            }
        }
        //followers
        GetResponseWrapper<List<Long>> followersResponse =getFollowersById(id,request).getBody();
        if(followersResponse==null){
            profileResponse.setFollowers(null);
            messageForResponse = messageForResponse+"no followers, ";
        }
        else{
            List<Long> followers = followersResponse.getResponse();
            if(followers!=null){
                messageForResponse = messageForResponse+followersResponse.getMessage()+", ";
                List<UserIconDto> followersIcons = new ArrayList<>();
                for(int i = 0;i<followers.size();i++){
                    UserIconDto userIconDto = new UserIconDto();
                    userIconDto.setUserId(followers.get(i));
                    //bunu eğer service'ten çeksem sıçardım.
                    userIconDto.setProfilePhotoPath(userRepository.getUserById(followers.get(i)).getProfilePicturePath());
                    followersIcons.add(userIconDto);
                }
                profileResponse.setFollowers(followersIcons);
            }
            else{
                profileResponse.setFollowers(null);
            }
        }

        //follows
        GetResponseWrapper<List<Long>> followsResponse =getFollowsById(id,request).getBody();
        if(followsResponse==null){
            profileResponse.setFollows(null);
            messageForResponse = messageForResponse+"no follows, ";
        }
        else{
            List<Long> follows = followsResponse.getResponse();
            if(follows!=null){
                messageForResponse = messageForResponse+followsResponse.getMessage()+", ";
                List<UserIconDto> followsIcons = new ArrayList<>();
                for(int i = 0;i<follows.size();i++){
                    UserIconDto userIconDto = new UserIconDto();
                    userIconDto.setUserId(follows.get(i));
                    //bunu eğer service'ten çeksem sıçardım.
                    userIconDto.setProfilePhotoPath(userRepository.getUserById(follows.get(i)).getProfilePicturePath());
                    followsIcons.add(userIconDto);
                }
                profileResponse.setFollows(followsIcons);
            }

        }

        //DONE

        profileResponse.setMessage(messageForResponse);
        return ResponseEntity.status(HttpStatus.OK).body(profileResponse);
    }
    public PhotoDtoForProfile photoToDtoConverter(Photo photo){
        PhotoDto photoDto = new PhotoDto();
        PhotoDtoForProfile photoDtoForProfile = new PhotoDtoForProfile();

        photoDto.setUserId(photo.getUser().getId());
        photoDto.setPath(photo.getPath());
        photoDto.setForGallery(photo.isForGallery());
        photoDto.setDescription(photo.getDescription());
        photoDto.setForSave(photo.isForSave());
        photoDto.setOpen(photo.isOpen());

        photoDtoForProfile.setPhotoDto(photoDto);
        photoDtoForProfile.setPhotoId(photo.getId());

        return photoDtoForProfile;

    }
}


