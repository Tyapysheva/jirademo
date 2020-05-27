package com.example.demo;

import com.example.demo.dataServices.DashboardDataService;
import com.example.demo.dataServices.IssueDataService;
import com.example.demo.dataServices.IssueStatusDataService;
import com.example.demo.dataServices.IssueTypeDataService;
import com.example.demo.dataServices.PriorityDataService;
import com.example.demo.dataServices.ProjectDataService;
import com.example.demo.dataServices.UserDataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;


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
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Bean
    public IssueDataService issueDataService() {
        return new IssueDataService();
    }

    @Bean
    public IssueTypeDataService issueTypeDataService() {
        return new IssueTypeDataService();
    }

    @Bean
    public IssueStatusDataService issueStatusDataService() {
        return new IssueStatusDataService();
    }

    @Bean
    public ProjectDataService projectDataService() {
        return new ProjectDataService();
    }

    @Bean
    public PriorityDataService priorityDataService() {
        return new PriorityDataService();
    }

    @Bean
    public UserDataService userDataService() {
        return new UserDataService();
    }

    @Bean
    public DashboardDataService dashboardDataService() {
        return new DashboardDataService();
    }

}