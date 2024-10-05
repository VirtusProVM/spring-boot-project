package com.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter")
public class Recruiter implements java.io.Serializable {

    @Id
    private int recruiterID;

    @OneToOne
    @JoinColumn(name = "recruiterID")
    @MapsId
    private Users userID;

    private String firstname;

    private String lastname;

    private String country;

    private String state;

    private String company;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public Recruiter() {}

    public Recruiter(Users userID) {
        this.userID = userID;
    }

    public Recruiter(int recruiterID, Users userID, String firstname, String lastname, String country, String state,
                     String company, String profilePhoto) {
        this.recruiterID = recruiterID;
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.state = state;
        this.company = company;
        this.profilePhoto = profilePhoto;
    }

    public int getRecruiterID() {
        return recruiterID;
    }

    public void setRecruiterID(int recruiterID) {
        this.recruiterID = recruiterID;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Transient
    public String getPhotosImagePath() {
        if (profilePhoto == null) return null;
        return "/photos/recruiter/" + recruiterID + "/" + profilePhoto;
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "recruiterID=" + recruiterID +
                ", userID=" + userID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", company='" + company + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
