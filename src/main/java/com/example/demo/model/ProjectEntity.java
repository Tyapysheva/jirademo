package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Project")
public class ProjectEntity{
    @Id
    private Long id;
    @Column(name = "keyP")
    private String key;
    private String name;
    @OneToMany(mappedBy = "projectS")
    private List<SprintEntity> sprints = new ArrayList<SprintEntity>();
    @OneToMany(mappedBy = "project")
    private List<IssueEntity> issues = new ArrayList<IssueEntity>();
    private String projectTypeKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SprintEntity> getSprints() {
        return sprints;
    }

    public void setSprints(List<SprintEntity> sprints) {
        this.sprints = sprints;
    }

    public List<IssueEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueEntity> issues) {
        this.issues = issues;
    }

    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }
}
