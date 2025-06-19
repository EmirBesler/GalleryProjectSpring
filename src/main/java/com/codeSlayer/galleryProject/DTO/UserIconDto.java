package com.codeSlayer.galleryProject.DTO;

public class UserIconDto {
    private Long userId;
    private String profilePhotoPath;


    public UserIconDto() {

    }
    public UserIconDto(Long userId, String profilePhotoPath) {
        this.userId = userId;
        this.profilePhotoPath = profilePhotoPath;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

}
