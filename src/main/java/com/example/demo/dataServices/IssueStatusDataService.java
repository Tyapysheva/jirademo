package com.example.demo.dataServices;

import com.example.demo.mapperClass.IssueStatus;
import com.example.demo.model.IssueStatusEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IssueStatusDataService {
    @Autowired
    private ModelMapper modelMapper;

    @Value("${credentials.username}")
    private String username;

    @Value("${credentials.token}")
    private String token;

    @Value("${jira.address}")
    private String address;

    public Iterable<IssueStatusEntity> getAll() {
        HttpResponse<IssueStatus[]> response = null;
        List<IssueStatusEntity> result = new ArrayList<>();
        try {
            response = Unirest.get(String.format("%s/rest/api/2/status", this.address))
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(IssueStatus[].class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            List<IssueStatus> body = Arrays.asList(response.getBody());
            result = body.stream()
                    .map(x->modelMapper.map(x, IssueStatusEntity.class))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
