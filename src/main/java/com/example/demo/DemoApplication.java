package com.example.demo;

import com.example.demo.mapperClass.Dashboard;
import com.example.demo.mapperClass.DashboardResponse;
import com.example.demo.mapperClass.FieldsResponse;
import com.example.demo.mapperClass.IssueResponse;
import com.example.demo.model.DashboardEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;


@SpringBootApplication
public class DemoApplication {
    static {
        Unirest.setTimeouts(1000, 5000);

        Unirest.setObjectMapper(new ObjectMapper() {
            private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                return gson.fromJson(value, valueType);
            }

            @Override
            public String writeValue(Object value) {
                return gson.toJson(value);
            }
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        HttpResponse<FieldsResponse> response = null;
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/search?jql=project=HUQG&maxResults=1")
                    .basicAuth("onelovezenit@gmail.com","dw2Xw44FxbRiDXvpbs4NBFFD")
                    .header("Accept","application/json")
                    .asObject(FieldsResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            FieldsResponse body = response.getBody();
            System.out.println(Arrays.toString(body.fields));
        }

//        JSONObject myObj = response.getBody().getObject();
//        System.out.println(myObj);

    }


}
