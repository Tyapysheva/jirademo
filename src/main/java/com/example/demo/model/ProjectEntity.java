package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name="Project")
public class ProjectEntity{
    @Id
    private Long id;
    @Column
    private String key;
    private String name;
    private ArrayList<IssueEntity> Issues;
    private String projectTypeKey;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public ArrayList<IssueEntity> getIssues() {
        return Issues;
    }

    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIssues(ArrayList<IssueEntity> issues) {
        Issues = issues;
    }

    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }
}
