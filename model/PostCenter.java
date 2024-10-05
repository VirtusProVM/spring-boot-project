package com.jobportal.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class PostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;

    @Length(max = 10000)
    private String jobDescription;

    private String jobTitle;

    private String jobType;

    @Transient
    private Boolean isActive;

    @Transient
    private Boolean isSaved;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;

    private String remote;

    private String salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyID", referencedColumnName = "companyID")
    private Company companyID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locationID", referencedColumnName = "locationID")
    private Location locationID;

    @ManyToOne
    @JoinColumn(name = "postedByID", referencedColumnName = "userID")
    private Users userID;

    public PostCenter() {}

    public PostCenter(Integer postID, String jobDescription, String jobTitle, String jobType, Boolean isActive, Boolean isSaved,
                      Date postedDate, String remote, String salary, Company companyID, Location locationID, Users userID) {
        this.postID = postID;
        this.jobDescription = jobDescription;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.postedDate = postedDate;
        this.remote = remote;
        this.salary = salary;
        this.companyID = companyID;
        this.locationID = locationID;
        this.userID = userID;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Boolean saved) {
        isSaved = saved;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String resume) {
        this.remote = remote;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Company getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Company companyID) {
        this.companyID = companyID;
    }

    public Location getLocationID() {
        return locationID;
    }

    public void setLocationID(Location locationID) {
        this.locationID = locationID;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "PostCenter{" +
                "postID=" + postID +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobType='" + jobType + '\'' +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", postedDate=" + postedDate +
                ", remote='" + remote + '\'' +
                ", salary='" + salary + '\'' +
                ", companyID=" + companyID +
                ", locationID=" + locationID +
                ", userID=" + userID +
                '}';
    }
}
