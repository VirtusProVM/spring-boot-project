package com.jobportal.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "worker_type")
public class WorkerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeID;

    @Column(name = "typeName")
    private String typeName;

    @OneToMany(targetEntity = Users.class, mappedBy = "workerTypeID", cascade = CascadeType.ALL)
    private List<Users> users;

    public WorkerType() {}

    public WorkerType(int typeID, String typeName, List<Users> users) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.users = users;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "WorkerType{" +
                "typeID=" + typeID +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
