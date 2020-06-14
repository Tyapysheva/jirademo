package com.example.demo.mapperClass;

import java.util.ArrayList;

public class Role {
    private Long id;
    private String name;
    private String description;

    private ArrayList<Actor> actors;

    public ArrayList<Actor> getActors() {
        return actors;
    }
}