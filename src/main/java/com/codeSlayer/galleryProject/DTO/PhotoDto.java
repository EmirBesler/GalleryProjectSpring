package com.codeSlayer.galleryProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhotoDto {
    private String path;
    private String description;
    private boolean isOpen;
    private boolean isForGallery;
    private boolean isForSave;
    private Long userId;


    public PhotoDto() {

    }
    public PhotoDto(String path, String description, boolean isOpen, boolean isForGallery, boolean isForSave,Long userId) {
        this.path = path;
        this.description = description;
        this.isOpen = isOpen;
        this.isForGallery = isForGallery;
        this.isForSave = isForSave;
        this.userId = userId;
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

    @JsonProperty("isOpen")
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }

    @JsonProperty("isForGallery")
    public boolean isForGallery() {
        return isForGallery;
    }
    public void setForGallery(boolean forGallery) {
        isForGallery = forGallery;
    }

    @JsonProperty("isForSave")
    public boolean isForSave() {
        return isForSave;
    }
    public void setForSave(boolean isForSave) {
        this.isForSave = isForSave;
    }


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
