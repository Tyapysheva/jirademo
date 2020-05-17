package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="Issues")
public class IssueEntity {
    @Id
    private Long id;
    @Column
    private String key;
    private Date created;
    private Integer aggregatetimeoriginalestimate;
    private Integer timeestimate;
    private Integer timespent;
    private Long idProject;
    private Long idSprint;
    private Long idUser;
    private Long idIssueType;
    private Long idPriority;
    private Long idStatusIssue;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Date getCreated() {
        return created;
    }

    public Integer getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    public Integer getTimeestimate() {
        return timeestimate;
    }

    public Integer getTimespent() {
        return timespent;
    }

    public Long getIdProject() {
        return idProject;
    }

    public Long getIdSprint() {
        return idSprint;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdIssueType() {
        return idIssueType;
    }

    public Long getIdPriority() {
        return idPriority;
    }

    public Long getIdStatusIssue() {
        return idStatusIssue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setAggregatetimeoriginalestimate(Integer aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    public void setTimeestimate(Integer timeestimate) {
        this.timeestimate = timeestimate;
    }

    public void setTimespent(Integer timespent) {
        this.timespent = timespent;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public void setIdSprint(Long idSprint) {
        this.idSprint = idSprint;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setIdIssueType(Long idIssueType) {
        this.idIssueType = idIssueType;
    }

    public void setIdPriority(Long idPriority) {
        this.idPriority = idPriority;
    }

    public void setIdStatusIssue(Long idStatusIssue) {
        this.idStatusIssue = idStatusIssue;
    }
}