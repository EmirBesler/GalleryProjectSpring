package com.codeSlayer.galleryProject.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Follow")

public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userIdIng;

    @ManyToOne
    private User userIdEd;


    public Follow() {

    }
    public Follow(User userIdIng, User userIdEd) {
        this.userIdIng = userIdIng;
        this.userIdEd = userIdEd;

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUserIdIng() {
        return userIdIng;
    }
    public void setUserIdIng(User userIdIng) {
        this.userIdIng = userIdIng;
    }
    public User getUserIdEd() {
        return userIdEd;
    }
    public void setUserIdEd(User userIdEd) {
        this.userIdEd = userIdEd;
    }

}
