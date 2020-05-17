package com.example.demo;

import com.example.demo.mapperClass.Issue;
import com.example.demo.mapperClass.IssueResponse;
import com.example.demo.model.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IssueDAL {
    @Autowired
    private ModelMapper modelMapper;

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";

    public List<IssueEntity> getIssues() {
        HttpResponse<IssueResponse> response = null;
        List<IssueEntity> result = new ArrayList<>();
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/search?jql=project=HUQG&maxResults=50")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(IssueResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            IssueResponse body = response.getBody();
            result = body.issues.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
        }
        return result;
    }

    private IssueEntity convertToEntity(Issue issue) {
        IssueEntity issueEntity = modelMapper.map(issue, IssueEntity.class);
        issueEntity.setAggregatetimeoriginalestimate(issue.fields.aggregatetimeoriginalestimate);
        issueEntity.setTimespent(issue.fields.timespent);
        issueEntity.setIssueType(modelMapper.map(issue.fields.issuetype, IssueTypeEntity.class));
        issueEntity.setIssueStatus(modelMapper.map(issue.fields.status, IssueStatusEntity.class));
        issueEntity.setProject(modelMapper.map(issue.fields.project, ProjectEntity.class));
        issueEntity.setUser(modelMapper.map(issue.fields.assignee, UserEntity.class));
        return issueEntity;
    }
}
