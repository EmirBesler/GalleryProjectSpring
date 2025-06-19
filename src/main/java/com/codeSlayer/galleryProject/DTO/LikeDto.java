package com.codeSlayer.galleryProject.DTO;

import com.codeSlayer.galleryProject.Entity.Like;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeDto {
    @JsonProperty("photoId")
    private Long photoId;

    @JsonProperty("userId")
    private Long userId;

    public LikeDto(){

    }
    public LikeDto(Long photoId, Long userId) {
        this.photoId = photoId;
    }
    public Long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
