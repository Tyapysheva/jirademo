package com.example.demo.dataServices;


import com.example.demo.mapperClass.User;
import com.example.demo.model.UserEntity;
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

public class UserDataService {
    @Autowired
    private ModelMapper modelMapper;

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";

    public Iterable<UserEntity> getAll() {
        HttpResponse<User[]> response = null;
        List<UserEntity> result = new ArrayList<>();
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/users/search")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(User[].class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            List<User> body = Arrays.asList(response.getBody());
            result = body.stream()
                    .map(x->modelMapper.map(x, UserEntity.class))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
