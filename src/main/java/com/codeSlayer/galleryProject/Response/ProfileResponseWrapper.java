package com.codeSlayer.galleryProject.Response;


import com.codeSlayer.galleryProject.DTO.PhotoDtoForProfile;
import com.codeSlayer.galleryProject.DTO.UserIconDto;


import java.util.List;

public class ProfileResponseWrapper {
    private String message;
    private List<PhotoDtoForProfile> photos;
    private String username;
    private String profilePhotoPath;
    private String userDescription;
    private List<UserIconDto> followers;
    private List<UserIconDto> follows;


    public ProfileResponseWrapper() {

    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<PhotoDtoForProfile> getPhotos() {
        return photos;
    }
    public void setPhotos(List<PhotoDtoForProfile> photos) {
        this.photos = photos;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
    public String getUserDescription() {
        return userDescription;
    }
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }
    public List<UserIconDto> getFollowers() {
        return followers;
    }
    public void setFollowers(List<UserIconDto> followers) {
        this.followers = followers;
    }
    public List<UserIconDto> getFollows() {
        return follows;
    }
    public void setFollows(List<UserIconDto> follows) {
        this.follows = follows;
    }

}
