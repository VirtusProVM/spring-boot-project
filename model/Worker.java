package com.jobportal.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "worker")
public class Worker implements java.io.Serializable {

    @Id
    private Integer workerID;

    @OneToOne
    @JoinColumn(name = "workerID")
    @MapsId
    private Users users;

    private String city;

    private String country;

    private String employmentType;

    private String firstname;

    private String lastname;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    private String resume;

    private String state;

    private  String workAuthorization;

    @OneToMany(targetEntity = Skills.class, cascade = CascadeType.ALL, mappedBy = "worker")
    private List<Skills> skills;

    public Worker() {}

    public Worker(Users users) {
        this.users = users;
    }

    public Worker(Integer workerID, Users users, String city, String country, String employmentType, String firstname,
                  String lastname, String profilePhoto, String resume, String state, String workAuthorization,
                  List<Skills> skills) {
        this.workerID = workerID;
        this.users = users;
        this.city = city;
        this.country = country;
        this.employmentType = employmentType;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePhoto = profilePhoto;
        this.resume = resume;
        this.state = state;
        this.workAuthorization = workAuthorization;
        this.skills = skills;
    }

    public Integer getWorkerID() {
        return workerID;
    }

    public void setWorkerID(Integer workerID) {
        this.workerID = workerID;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Transient
    public String getPhotosImagePath() {
        if (profilePhoto == null || workerID == null) return null;
        return "photos/candidate/" + workerID + "/" + profilePhoto;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerID=" + workerID +
                ", users=" + users +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", resume='" + resume + '\'' +
                ", state='" + state + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                '}';
    }
}
