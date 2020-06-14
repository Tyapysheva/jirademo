package com.example.demo.mapperClass;

import java.util.Date;

public class IssueFields {

    public String summary;
    public Date statuscategorychangedate; // это поле - дата изменения статуса задачи, напиши, если оно необходимо
    public int aggregatetimeoriginalestimate;
    public int timeestimate;
    public int timespent;
    public IssueType issuetype;
    public Project project;
    public User assignee;
    public IssueStatus status;
    public IssuePriority priority;
    public String[] customfield_10020;
    public String created;
    public String duedate;
//    public User reporter;

    @Override
    public String toString() {
        return this.statuscategorychangedate + "|" + this.timespent + "|" + this.aggregatetimeoriginalestimate + "|";
    }
}
