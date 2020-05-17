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
    private String self;
    private String name;
    @OneToMany(mappedBy = "priority")
    private List<IssueEntity> issues = new ArrayList<IssueEntity>();
}