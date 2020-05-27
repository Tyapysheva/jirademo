package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class SprintEntity {
    @Id
    private Long id;
    @Column
    private String state;
    private String name;
    private String goal;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity projectS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public ProjectEntity getProjectS() {
        return projectS;
    }

    public void setProjectS(ProjectEntity projectS) {
        this.projectS = projectS;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!SprintEntity.class.isInstance(obj)) {
            return false;
        }
        SprintEntity sprintEntity = SprintEntity.class.cast(obj);
        return sprintEntity.id == this.id && sprintEntity.name.equals(this.name);
    }
}
