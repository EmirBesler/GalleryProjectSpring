package com.codeSlayer.galleryProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FollowRequestDto {
    //Person who send Request
    @JsonProperty("userIdSend")
    private Long userIdSend;



    @JsonProperty("userIdReceive")
    private Long userIdReceive;


    public FollowRequestDto() {

    }
    public FollowRequestDto(Long userIdSend, Long userIdReceive) {
        this.userIdSend = userIdSend;
        this.userIdReceive = userIdReceive;
    }
    public Long getUserIdSend() {
        System.out.println("this is the send id:"+userIdSend);
        return userIdSend;
    }
    public void setUserIdSend(Long userIdSend) {
        this.userIdSend = userIdSend;
    }
    public Long getUserIdReceive() {
        System.out.println("this is the receive id:"+userIdReceive);
        return userIdReceive;
    }
    public void setUserIdReceive(Long userIdReceive) {
        this.userIdReceive = userIdReceive;
    }
}
