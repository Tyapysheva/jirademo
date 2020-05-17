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
public class IssueTypeEntity {
    @Id
    private Long id;
    @Column
    private String description;
    private String name;
    private boolean subtask;
    @OneToMany(mappedBy = "issueType")
    private List<IssueEntity> issues;
}
