package com.jobportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(unique = true)
    private String email;

    private boolean isActive;

    @NotEmpty
    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registerDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typeID", referencedColumnName = "typeID")
    private WorkerType workerTypeID;

    public Users() {}

    public Users(int userID, String email, boolean isActive, String password, Date registerDate, WorkerType workerTypeID) {
        this.userID = userID;
        this.email = email;
        this.isActive = isActive;
        this.password = password;
        this.registerDate = registerDate;
        this.workerTypeID = workerTypeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public WorkerType getWorkerTypeID() {
        return workerTypeID;
    }

    public void setWorkerTypeID(WorkerType workerTypeID) {
        this.workerTypeID = workerTypeID;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", registerDate=" + registerDate +
                ", workerTypeID=" + workerTypeID +
                '}';
    }
}
