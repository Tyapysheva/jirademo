package com.example.demo.viewModels;

import com.example.demo.model.IssueEntity;

import java.util.stream.StreamSupport;

public class IssueLoadModel {
    public IssueEntity issue;
    public double load;

    public IssueLoadModel(IssueEntity issue, double load) {
        this.issue = issue;
        this.load = load;
    }

    public void removeLoad(double load) {
        this.load -= load;
    }

    public static double getSumLoad(Iterable<IssueLoadModel> loadData) {
        return StreamSupport.stream(loadData.spliterator(), false).mapToDouble(x->x.load).sum();
    }
}
