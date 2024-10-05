package com.jobportal.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "job_apply", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userID", "job"})})
public class JobApply implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applyID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "workerID")
    private Worker userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "postID")
    private PostCenter job;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date applyDate;

    private String coverLetter;

    public JobApply() {}

    public JobApply(Integer applyID, Worker userId, PostCenter job, Date applyDate, String coverLetter) {
        this.applyID = applyID;
        this.userId = userId;
        this.job = job;
        this.applyDate = applyDate;
        this.coverLetter = coverLetter;
    }

    public Integer getApplyID() {
        return applyID;
    }

    public void setApplayID(Integer applayID) {
        this.applyID = applayID;
    }

    public Worker getUserId() {
        return userId;
    }

    public void setUserId(Worker userId) {
        this.userId = userId;
    }

    public PostCenter getJob() {
        return job;
    }

    public void setJob(PostCenter job) {
        this.job = job;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    @Override
    public String toString() {
        return "JobApply{" +
                "applayID=" + applyID +
                ", userId=" + userId +
                ", job=" + job +
                ", applyDate=" + applyDate +
                ", coverLetter='" + coverLetter + '\'' +
                '}';
    }
}
