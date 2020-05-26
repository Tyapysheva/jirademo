package com.example.demo.dataServices;

import com.example.demo.mapperClass.Project;
import com.example.demo.model.PriorityEntity;
import com.example.demo.model.ProjectEntity;
import com.example.demo.mapperClass.Priority;
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

public class PriorityDataService {
    @Autowired
    private ModelMapper modelMapper;

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";

    public Iterable<PriorityEntity> getAll() {
        HttpResponse<Priority[]> response = null;
        List<PriorityEntity> result = new ArrayList<>();
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/priority")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(Priority[].class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            List<Priority> body = Arrays.asList(response.getBody());
            result = body.stream()
                    .map(x->modelMapper.map(x, PriorityEntity.class))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
