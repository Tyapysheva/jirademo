package com.example.demo.viewModels;

import java.util.*;

public class UserLoadCompactModel {
    public String userName;
    public String userRoles;
    public Map<String, Double> load;

    public UserLoadCompactModel(UserLoadModel model, Iterable<String> days) {
        this.userName = model.userName;
        this.userRoles = model.userRoles;
        this.load = new HashMap<String, Double>();

        for (String day: days) {
            this.load.put(day, model.getLoad(day) * 100);
        }
    }
}
