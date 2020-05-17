package com.example.demo.mapperClass;

public class Issue {
    public Long id;
    public String key;
    public IssueFields fields;

    @Override
    public String toString() {
        return this.id+"|"+this.key+"|";
    }
}
