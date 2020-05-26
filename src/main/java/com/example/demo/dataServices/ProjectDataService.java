package com.example.demo.dataServices;


import com.example.demo.mapperClass.Project;
import com.example.demo.model.ProjectEntity;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDataService {
    @Autowired
    private ModelMapper modelMapper;

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";

    public Iterable<ProjectEntity> getAll() {
        HttpResponse<Project[]> response = null;
        List<ProjectEntity> result = new ArrayList<>();
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/project")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(Project[].class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            List<Project> body = Arrays.asList(response.getBody());
            result = body.stream()
                    .map(x->modelMapper.map(x, ProjectEntity.class))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
