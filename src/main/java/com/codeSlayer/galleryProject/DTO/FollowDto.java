package com.codeSlayer.galleryProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FollowDto {

    @JsonProperty("userIdFollowing")
    private Long userIdFollowing;

    @JsonProperty("userIdFollowed")
    private Long userIdFollowed;

    public FollowDto() {

    }
    public FollowDto(Long userIdFollowing, Long userIdFollowed) {
        this.userIdFollowing = userIdFollowing;
        this.userIdFollowed = userIdFollowed;
    }
    public Long getUserIdFollowing() {
        return userIdFollowing;
    }
    public void setUserIdFollowing(Long userIdFollowing) {
        this.userIdFollowing = userIdFollowing;
    }
    public Long getUserIdFollowed() {
        return userIdFollowed;
    }
    public void setUserIdFollowed(Long userIdFollowed) {
        this.userIdFollowed = userIdFollowed;
    }

}
