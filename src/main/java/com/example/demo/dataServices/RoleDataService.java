package com.example.demo.dataServices;


import com.example.demo.mapperClass.Role;
import com.example.demo.mapperClass.User;
import com.example.demo.model.ProjectEntity;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RoleDataService {
    @Autowired
    private ModelMapper modelMapper;

    @Value("${credentials.username}")
    private String username;

    @Value("${credentials.token}")
    private String token;

    @Value("${jira.address}")
    private String address;

    public Iterable<RoleEntity> getAll(Iterable<String> projectKeys) {
        Map<Long, RoleEntity> result = new HashMap<>();
        for (String key: projectKeys) {
            result.putAll(getAll(key));
        }
        return result.values();
    }

    public Map<Long, RoleEntity> getAll(String projectKey) {
        HttpResponse<Role[]> response = null;
        Map<Long, RoleEntity> result = new HashMap<>();
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
                    .map(x->modelMapper.map(x, RoleEntity.class))
                    .collect(Collectors.toMap(RoleEntity::getId, x->x));
        }
        return result;
    }

    public Iterable<UserEntity> setUserRoles(Iterable<UserEntity> users, Iterable<RoleEntity> roles, Iterable<String> projectKeys) {
        Map<String, UserEntity> userMap = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toMap(UserEntity::getId, u -> u));
        for (String projectKey: projectKeys) {
            for (RoleEntity role: roles) {
                this.setUserRole(userMap, role, projectKey);
            }
        }
        return userMap.values();
    }

    private void setUserRole(Map<String, UserEntity> users, RoleEntity role, String projectKey) {
        HttpResponse<Role> response = null;
        try {
            response = Unirest.get(String.format("%s/rest/api/2/project/%s/role/%d", this.address, projectKey, role.getId()))
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(Role.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            Role body = response.getBody();
            List<String> accountIds = body.getActors().stream()
                    .map(x->x.getAccountId())
                    .collect(Collectors.toList());
            for (String accountId : accountIds){
                if (users.containsKey(accountId) && !users.get(accountId).hasRole(role.getId())) {
                    users.get(accountId).addRole(role);
                }
            }
        }
    }
}
