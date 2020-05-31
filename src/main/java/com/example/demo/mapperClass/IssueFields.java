package com.example.demo.mapperClass;

import java.util.Date;

public class IssueFields {

    public Date statuscategorychangedate; // это поле - дата изменения статуса задачи, напиши, если оно необходимо
    public int timespent;
    public int aggregatetimeoriginalestimate;
    public int timeoriginalestimate;
    public IssueType issuetype;
    public Project project;
    public User assignee;
    public IssueStatus status;
    public IssuePriority priority;
    public String[] customfield_10020;
    public String duedate;
//    public User reporter;

    @Override
    public String toString() {
        return this.statuscategorychangedate + "|" + this.timespent + "|" + this.aggregatetimeoriginalestimate + "|";
    }
}
