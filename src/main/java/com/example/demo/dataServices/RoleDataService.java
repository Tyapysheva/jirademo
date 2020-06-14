package com.example.demo.dataServices;


import com.example.demo.mapperClass.Role;
import com.example.demo.model.ProjectEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

public class RoleDataService {
    @Autowired
    private ModelMapper modelMapper;

    @Value("${credentials.username}")
    private String username;

    @Value("${credentials.token}")
    private String token;

    @Value("${jira.address}")
    private String address;

    public Map<String, Iterable<Role>> getAll(Iterable<String> projectKeys) {
        Map<String, Iterable<Role>> result = new HashMap<>();
        for (String key: projectKeys) {
            result.put(key, getAll(key));
        }
        return result;
    }

    public Iterable<Role> getAll(String projectKey) {
        HttpResponse<Role[]> response = null;
        List<Role> result = new ArrayList<>();
        try {
            response = Unirest.get(String.format("%s/rest/api/2/project/%s/roledetails", this.address, projectKey))
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(Role[].class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            List<Role> body = Arrays.asList(response.getBody());
            result = body.stream()
                    .map(x->modelMapper.map(x, Role.class))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
