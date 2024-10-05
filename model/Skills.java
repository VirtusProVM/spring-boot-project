package com.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillID;

    private String experienceLevel;

    private String name;

    private String yearsOfExperience;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker")
    private Worker worker;

    public Skills() {}

    public Skills(Integer skillID, String experienceLevel, String name, String yearsOfExperience, Worker worker) {
        this.skillID = skillID;
        this.experienceLevel = experienceLevel;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.worker = worker;
    }

    public Integer getSkillID() {
        return skillID;
    }

    public void setSkillID(Integer skillID) {
        this.skillID = skillID;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        return "Skills{" +
                "skillID=" + skillID +
                ", expirienceLevel='" + experienceLevel + '\'' +
                ", name='" + name + '\'' +
                ", yearsOfExpirience='" + yearsOfExperience + '\'' +
                ", worker=" + worker +
                '}';
    }
}
