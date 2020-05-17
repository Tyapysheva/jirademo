package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class IssueStatusEntity {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "issueStatus")
    private List<IssueEntity> issues = new ArrayList<IssueEntity>();


}