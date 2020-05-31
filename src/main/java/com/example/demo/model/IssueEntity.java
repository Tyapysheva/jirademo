package com.example.demo.model;

import com.example.demo.mapperClass.IssueType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Issues")
public class IssueEntity {
    @Id
    private Long id;
    @Column(name = "keyIs")
    private String key;
    private Date created;
    @Column(name = "aggregate")
    private Integer aggregatetimeoriginalestimate;
    private Integer timeestimate;
    private Integer timespent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private SprintEntity sprint;

    @ManyToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "issueType_id")
    private IssueTypeEntity issueType;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private PriorityEntity priority;

    @ManyToOne
    @JoinColumn(name = "statusIssue_id")
    private IssueStatusEntity issueStatus;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    public void setAggregatetimeoriginalestimate(Integer aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    public Integer getTimeestimate() {
        return timeestimate;
    }

    public void setTimeestimate(Integer timeestimate) {
        this.timeestimate = timeestimate;
    }

    public Integer getTimespent() {
        return timespent;
    }

    public void setTimespent(Integer timespent) {
        this.timespent = timespent;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public SprintEntity getSprint() {
        return sprint;
    }

    public void setSprint(SprintEntity sprint) {
        this.sprint = sprint;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public IssueTypeEntity getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueTypeEntity issueType) {
        this.issueType = issueType;
    }

    public PriorityEntity getPriority() {
        return priority;
    }

    public void setPriority(PriorityEntity priority) {
        this.priority = priority;
    }

    public IssueStatusEntity getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatusEntity issueStatus) {
        this.issueStatus = issueStatus;
    }
}