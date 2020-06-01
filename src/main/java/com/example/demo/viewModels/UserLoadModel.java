package com.example.demo.viewModels;

import java.util.HashMap;

public class UserLoadModel {
    public String userName;
    public HashMap<String, Double> load;

    public UserLoadModel(String userName) {
        this.userName = userName;
        this.load = new HashMap<String, Double>();
    }

    public void addLoad(String day, double load) {
        this.load.put(day, load);
    }
}
