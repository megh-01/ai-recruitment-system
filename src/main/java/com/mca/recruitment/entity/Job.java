package com.mca.recruitment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String companyName;
    private String location;
    private String requiredSkills;
    private String experienceRequired;

    @Column(length = 5000)
    private String description;

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getExperienceRequired() {
        return experienceRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExperienceRequired(String experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}