package com.example.demo.viewModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserLoadModel {
    public String userName;
    public HashMap<String, List<IssueLoadModel>> load;

    public UserLoadModel(String userName) {
        this.userName = userName;
        this.load = new HashMap<>();

    }

    public void addLoad(String day, IssueLoadModel load) {
        if (this.load.containsKey(day)) {
            this.load.get(day).add(load);
        }
        else {
            List<IssueLoadModel> loadData = new ArrayList<>();
            loadData.add(load);
            this.load.put(day, loadData);
        }
    }

    public void addLoad(String day) {
        this.load.put(day, new ArrayList<>());
    }

    public double getLoad(String day) {
        List<IssueLoadModel> defaultLoad = new ArrayList<>();
        List<IssueLoadModel> loadData = new ArrayList<>();
        try {
            loadData = this.load.getOrDefault(day, defaultLoad);
        } catch (Exception e) {
        }
        return loadData.stream().mapToDouble(x->x.load).sum();
    }

    public String getStringLoad(String day) {
        double load = this.getLoad(day);
        return String.format("%4.3f %%", load * 100);
    }
}
