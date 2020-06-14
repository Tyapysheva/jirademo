package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="User")
public class UserEntity {
    @Id
    @Column(name = "Id")
    private String accountId;
    private String displayName;
    private String accountType;
   // private ArrayList<ProjectEntity> projects;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<RoleEntity> roles;

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
        if (issues == null) {
            issues = new ArrayList<>();
        }
        return issues;
    }

    public Iterable<RoleEntity> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void addRole(RoleEntity role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    public boolean hasRole(Long roleId) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        Optional<RoleEntity> role = roles.stream().filter(x->x.getId() == roleId).findFirst();
        return role.isPresent();
    }
}
