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
    @Column
    private String keyP;
    private String name;
    @OneToMany(mappedBy = "projectS")
    private List<SprintEntity> sprints = new ArrayList<SprintEntity>();
    @OneToMany(mappedBy = "project")
    private List<IssueEntity> issues = new ArrayList<IssueEntity>();
    private String projectTypeKey;
}
