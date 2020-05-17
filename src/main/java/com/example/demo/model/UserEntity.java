package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
public class UserEntity {
    @Id
    private Long id;
    private String displayName;
   // private ArrayList<ProjectEntity> projects;
    @OneToMany(mappedBy = "user")
    private List<IssueEntity> issues = new ArrayList<IssueEntity>();


}
