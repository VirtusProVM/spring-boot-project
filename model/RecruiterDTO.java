package com.jobportal.model;

public class RecruiterDTO {

    private Long totalCandidates;
    private Integer postID;
    private String jobTitle;
    private Location locationID;
    private Company companyID;

    public RecruiterDTO(Long totalCandidates, Integer jobPostID, String jobTitle, Location locationID, Company companyID) {
        this.totalCandidates = totalCandidates;
        this.postID = jobPostID;
        this.jobTitle = jobTitle;
        this.locationID = locationID;
        this.companyID = companyID;
    }

    public Long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(Long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Location getLocationID() {
        return locationID;
    }

    public void setLocationID(Location locationID) {
        this.locationID = locationID;
    }

    public Company getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Company companyID) {
        this.companyID = companyID;
    }
}
