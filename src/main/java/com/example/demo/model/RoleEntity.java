package com.example.demo.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="Role")
public class RoleEntity {
    @Id
    @Column(name = "Id")
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    public Long getId() {
        return id;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }
}
