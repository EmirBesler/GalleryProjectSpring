package com.codeSlayer.galleryProject.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;

@Entity
@Table(name ="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @Column(name = "is_profile_open")
    @JsonProperty("isProfileOpen")
    private boolean isProfileOpen;

    @JsonProperty("profileDescription")
    @Column(name = "profile_description")
    private String profileDescription;

    @JsonProperty("profilePicturePath")
    @Column(name = "profile_picture_path")
    private String profilePicturePath;

    public User(String username, String password, String email, boolean isProfileOpen, String profileDescription, String profilePicturePath) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isProfileOpen = isProfileOpen;
        this.profileDescription = profileDescription;
        this.profilePicturePath = profilePicturePath;
    }
    public User() {

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isProfileOpen() {
        return isProfileOpen;
    }
    public void setProfileOpen(boolean profileOpen) {
        isProfileOpen = profileOpen;
    }
    public String getProfileDescription() {
        return profileDescription;
    }
    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
    public String getProfilePicturePath() {
        return profilePicturePath;
    }
    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }


}
