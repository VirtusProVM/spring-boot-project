package com.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userID", "job"})
})
public class JobWorkerSave implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "postID")
    private PostCenter job;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "workerID")
    private Worker userId;

    public JobWorkerSave() {}

    public JobWorkerSave(int id, PostCenter job, Worker userId) {
        this.id = id;
        this.job = job;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PostCenter getJob() {
        return job;
    }

    public void setJob(PostCenter job) {
        this.job = job;
    }

    public Worker getWorkerID() {
        return userId;
    }

    public void setWorkerID(Worker userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "JobWorkerSave{" +
                "id=" + id +
                ", job=" + job +
                ", userId=" + userId +
                '}';
    }
}
