package com.example.demo.dataServices;

import com.example.demo.mapperClass.Issue;
import com.example.demo.mapperClass.IssueResponse;
import com.example.demo.mapperClass.Project;
import com.example.demo.model.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class IssueDataService {
    @Autowired
    private ModelMapper modelMapper;
    private final Integer pageStart = 0;
    private final Integer pageSize = 50;

    private final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
    private final DateFormat dateTimeFormatVariant = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${credentials.username}")
    private String username;

    @Value("${credentials.token}")
    private String token;

    @Value("${jira.address}")
    private String address;

    public Iterable<IssueEntity> getAll(Iterable<String> projectKeys) {
        List<IssueEntity> result = new ArrayList<>();
        if (projectKeys != null && ((Collection<?>)projectKeys).size() > 0)
        {
            for(String k : projectKeys) {
                Integer startAt = pageStart - pageSize;
                IssueResponse issueResponse;
                do {
                    startAt += pageSize;
                    issueResponse = this.find(k, startAt, pageSize);
                    if (issueResponse != null && issueResponse.issues != null) {
                        List<IssueEntity> buffer = issueResponse.issues.stream()
                                .map(this::convertToEntity)
                                .collect(Collectors.toList());
                        result.addAll(buffer);
                    }
                }
                while(issueResponse != null && issueResponse.total > startAt + pageSize);
            };
        }
        return result;
    }

    private IssueResponse find(String project, Integer startAt, Integer maxResult) {
        HttpResponse<IssueResponse> response = null;
        IssueResponse result = null;
        String request = String.format("%s/rest/api/2/search?jql=project=%s&startAt=%d&maxResults=%d", this.address, project, startAt, maxResult);
        try {
            response = Unirest.get(request)
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(IssueResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            result = response.getBody();
        }
        return result;
    }

    private IssueEntity convertToEntity(Issue issue) {
        IssueEntity issueEntity = modelMapper.map(issue, IssueEntity.class);
        issueEntity.setAggregatetimeoriginalestimate(issue.fields.aggregatetimeoriginalestimate);
        issueEntity.setTimespent(issue.fields.timespent);
        issueEntity.setTimeestimate(issue.fields.timeestimate);
        issueEntity.setIssueType(modelMapper.map(issue.fields.issuetype, IssueTypeEntity.class));
        issueEntity.setIssueStatus(modelMapper.map(issue.fields.status, IssueStatusEntity.class));
        issueEntity.setProject(modelMapper.map(issue.fields.project, ProjectEntity.class));
        issueEntity.setUser(modelMapper.map(issue.fields.assignee, UserEntity.class));
        issueEntity.setPriority(modelMapper.map(issue.fields.priority, PriorityEntity.class));

        Optional<Date> createdDate = this.parseDate(issue.fields.created);
        if (createdDate.isPresent()) {
            issueEntity.setCreated(createdDate.get());
        }

        Optional<Date> dueDate = this.parseDate(issue.fields.duedate);
        if (dueDate.isPresent()) {
            issueEntity.setDueDate(dueDate.get());
        }

        Optional<String> sprintData = Arrays.stream(issue.fields.customfield_10020)
                .filter(x->x.contains("service.sprint.Sprint"))
                .findFirst();
        if (sprintData.isPresent()) {
            issueEntity.setSprint(parseSprint(sprintData.get()));
        }
        return issueEntity;
    }

    // Вытаскивает данные из строки а-ля "com.atlassian.greenhopper.service.sprint.Sprint@428da144[completeDate=<null>,endDate=2020-05-10T12:57:00.000Z,goal=Создать базу данных Проекта,id=3,name=Релиз 1,rapidViewId=4,sequence=3,startDate=2020-04-26T12:57:31.205Z,state=ACTIVE]"
    private SprintEntity parseSprint(String field) {
        if (!field.contains("service.sprint.Sprint")) {
            return null;
        }
        SprintEntity entity = new SprintEntity();
        Map<String, String> data = Arrays.asList(field.substring(field.indexOf('[') + 1, field.indexOf(']')).split(","))
                .stream()
                .map(x -> x.split("="))
                .collect(Collectors.toMap(x -> x[0], x -> x.length > 1 ? x[1] : ""));

        Optional<Date> startDate = this.parseDate(data.get("startDate"));
        Optional<Date> endDate = this.parseDate(data.get("endDate"));
        Optional<Date> completeDate = this.parseDate(data.get("completeDate"));

        entity.setId(Long.parseLong(data.get("id")));
        entity.setStartDate(startDate.get());
        entity.setEndDate(endDate.get());
        if (completeDate.isPresent()) {
            entity.setCompleteDate(completeDate.get());
        }
        entity.setName(data.get("name"));
        entity.setGoal(data.get("goal"));
        entity.setState(data.get("state"));
        entity.setSequence(Long.parseLong(data.get("sequence")));

        return entity;
    }

    private Optional<Date> parseDate(String input) {
        Optional<Date> result = Optional.empty();
        result = this.parseDate(dateTimeFormat, input);
        if (!result.isPresent()) {
            result = this.parseDate(dateTimeFormatVariant, input);

            if (!result.isPresent()) {
                result = this.parseDate(dateFormat, input);
            }
        }
        return result;
    }

    private Optional<Date> parseDate(DateFormat formatter, String input) {
        Optional<Date> result = Optional.empty();
        if (input != null && !input.isEmpty() && !input.equals("<null>")) {
            try {
                result = Optional.of(formatter.parse(input));
            } catch (ParseException e) {
            }
        }
        return result;
    }
}
