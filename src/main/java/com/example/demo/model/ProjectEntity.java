package com.example.demo.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="Project")
public class ProjectEntity{
    @Id
    private Long id;

    @Column(name = "keyP")
    private String key;

    private String name;

    @OneToMany(mappedBy = "projectS", fetch = FetchType.LAZY)
    private Collection<SprintEntity> sprints;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Collection<IssueEntity> issues;

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

    public Iterable<SprintEntity> getSprints() {
        return sprints;
    }

    public Iterable<IssueEntity> getIssues() {
        return issues;
    }

    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }
}
