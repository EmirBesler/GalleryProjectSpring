package com.codeSlayer.galleryProject.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "FollowRequest")

public class FollowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userSend;

    @ManyToOne
    private User userReceive;

    public FollowRequest() {
        super();
    }
    public FollowRequest(User userSend, User userReceive) {

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUserSend() {
        return userSend;
    }
    public void setUserSend(User userSend) {
        this.userSend = userSend;
    }
    public User getUserReceive() {
        return userReceive;
    }
    public void setUserReceive(User userReceive) {
        this.userReceive = userReceive;
    }


}
