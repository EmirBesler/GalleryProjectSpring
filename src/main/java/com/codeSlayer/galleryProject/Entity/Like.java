package com.codeSlayer.galleryProject.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "UserLike")

public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Photo photo;

    @ManyToOne
    private User user;


    public Like() {

    }
    public Like(Photo photo, User user) {
        this.photo = photo;
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Photo getPhoto() {
        return photo;
    }
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
