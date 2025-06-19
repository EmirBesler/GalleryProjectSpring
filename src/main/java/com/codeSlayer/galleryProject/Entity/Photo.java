package com.codeSlayer.galleryProject.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Photo")

public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;


    private String description;


    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "is_for_gallery")
    private boolean isForGallery;

    @Column(name = "is_for_save")
    private boolean isForSave;

    @ManyToOne
    private User user;


    public Photo() {

    }
    public Photo(String path, String description, boolean isOpen, boolean isForGallery, boolean isForSave, User user) {
        this.path = path;
        this.description = description;
        this.isOpen = isOpen;
        this.isForGallery = isForGallery;
        this.isForSave = isForSave;
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }
    public boolean isForGallery() {
        return isForGallery;
    }
    public void setForGallery(boolean forGallery) {
        isForGallery = forGallery;
    }
    public boolean isForSave() {
        return isForSave;
    }
    public void setForSave(boolean forSave) {
        isForSave = forSave;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }




}
