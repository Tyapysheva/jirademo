package com.example.demo.dataServices;

import com.example.demo.mapperClass.IssueType;
import com.example.demo.model.IssueTypeEntity;
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

public class IssueTypeDataService {
    @Autowired
    private ModelMapper modelMapper;

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";

    public Iterable<IssueTypeEntity> getAll() {
        HttpResponse<IssueType[]> response = null;
        List<IssueTypeEntity> result = new ArrayList<>();
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/issuetype")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(IssueType[].class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            List<IssueType> body = Arrays.asList(response.getBody());
            result = body.stream()
                    .map(x->modelMapper.map(x, IssueTypeEntity.class))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
