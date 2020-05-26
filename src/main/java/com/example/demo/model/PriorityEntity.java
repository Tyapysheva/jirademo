package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table
public class PriorityEntity {
    @Id
    private Long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "priority")
    private List<IssueEntity> issues = new ArrayList<IssueEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IssueEntity> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueEntity> issues) {
        this.issues = issues;
    }
}
