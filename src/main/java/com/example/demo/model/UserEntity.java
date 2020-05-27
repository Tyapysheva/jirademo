package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="User")
public class UserEntity {
    @Id
    @Column(name = "Id")
    private String accountId;
    private String displayName;
    private String accountType;
   // private ArrayList<ProjectEntity> projects;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<IssueEntity> issues;

    public String getId() {
        return accountId;
    }

    public void setId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Iterable<IssueEntity> getIssues() {
        return issues;
    }
}
