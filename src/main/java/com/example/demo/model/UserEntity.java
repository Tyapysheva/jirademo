package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name="User")
public class UserEntity {
    @Id
    private Long id;
    private String displayName;

    private ArrayList<ProjectEntity> projects;

    private ArrayList<IssueEntity> issues;

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<ProjectEntity> getProjects() {
        return projects;
    }

    public ArrayList<IssueEntity> getIssues() {
        return issues;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProjects(ArrayList<ProjectEntity> projects) {
        this.projects = projects;
    }

    public void setIssues(ArrayList<IssueEntity> issues) {
        this.issues = issues;
    }
}
