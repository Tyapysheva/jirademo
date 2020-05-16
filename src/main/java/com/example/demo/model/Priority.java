package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class Priority {
    @Id
    private Long id;
    @Column
    private String self;
    private String name;
    public Priority(){}

    public Long getId() {
        return id;
    }

    public String getSelf() {
        return self;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public void setName(String name) {
        this.name = name;
    }
}
