package com.codeSlayer.galleryProject.DTO;

public class PhotoDtoForProfile {
    private PhotoDto photoDto;
    private Long photoId;

    public PhotoDtoForProfile() {

    }
    public PhotoDtoForProfile(PhotoDto photoDto, Long photoId) {
        this.photoDto = photoDto;
    }
    public PhotoDto getPhotoDto() {
        return photoDto;
    }
    public void setPhotoDto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }
    public Long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

}
