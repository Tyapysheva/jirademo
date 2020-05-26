package com.example.demo.dataServices;

import com.example.demo.mapperClass.Dashboard;
import com.example.demo.mapperClass.DashboardResponse;
import com.example.demo.model.DashboardEntity;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardDataService {
    @Autowired
    private ModelMapper modelMapper;

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";
    private Dashboard dashboard;

    public Iterable<DashboardEntity> getAll() {
        HttpResponse<DashboardResponse> response = null;
        List<DashboardEntity> result = new ArrayList<>();
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/dashboard")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(DashboardResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            DashboardResponse body = response.getBody();
            result = body.dashboards.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
        }
        return result;
    }

    private DashboardEntity convertToEntity(Dashboard dashboard) {
        DashboardEntity dashboardEntity = modelMapper.map(dashboard, DashboardEntity.class);
        dashboardEntity.setId(dashboard.id);
        dashboardEntity.setName(dashboard.name);
        dashboardEntity.setView(dashboard.view);
         return dashboardEntity;
    }
}
