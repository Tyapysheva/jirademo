package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.DateTimeException;
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
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity projectS;


}
