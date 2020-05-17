package com.example.demo;

import com.example.demo.mapperClass.DashboardResponse;
import com.example.demo.model.IssueEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;
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

    private static String username = "onelovezenit@gmail.com";
    private static String token = "dw2Xw44FxbRiDXvpbs4NBFFD";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        IssueDAL issueDAL = new IssueDAL();
        List<IssueEntity> issues = issueDAL.getIssues();
        System.out.format("Была получена информация о %d задачах", issues.size());

        HttpResponse<DashboardResponse> response = null;
        try {
            response = Unirest.get("https://tyapysheva.atlassian.net/rest/api/2/search?jql=project=HUQG&maxResults=1")
                    .basicAuth(username, token)
                    .header("Accept", "application/json")
                    .asObject(DashboardResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            DashboardResponse body = response.getBody();
            //           System.out.println(Arrays.toString(body.fields));
        }

//        JSONObject myObj = response.getBody().getObject();
//        System.out.println(myObj);

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
}