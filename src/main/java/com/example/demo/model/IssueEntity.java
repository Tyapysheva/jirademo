package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="Issues")
public class IssueEntity {
    @Id
    private Long id;
    @Column
    private String keyIs;
    private Date created;
    @Column(name="aggregate")
    private Integer aggregatetimeoriginalestimate;
    private Integer timeestimate;
    private Integer timespent;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
    @ManyToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "issueType_id")
    private IssueType issueType;
    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "statusIssue_id")
    private StatusIssue statusIssue;

}