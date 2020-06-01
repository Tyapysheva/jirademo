package com.example.demo.dataServices;


import com.example.demo.mapperClass.User;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.viewModels.UserLoadModel;
import com.example.demo.viewModels.UserLoadViewModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserDataService {
    @Autowired
    private ModelMapper modelMapper;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM'\n'E");
    private final Integer secondsInHour = 3600;

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

    public UserLoadViewModel prepare(Iterable<UserEntity> users) {
        List<UserEntity> requiredUsers = StreamSupport.stream(users.spliterator(), false)
                .filter(x -> "atlassian".equals(x.getAccountType()))
                .collect(Collectors.toList());
        List<UserLoadModel> userLoads = new ArrayList<>();
        List<String> days = new ArrayList<>();

        LocalDate currentLocalDate = Instant.ofEpochMilli(new Date().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = currentLocalDate;

        for (UserEntity user : requiredUsers) {
            UserLoadModel loadData = new UserLoadModel(user.getDisplayName());

            List<IssueEntity> currentIssues = StreamSupport.stream(user.getIssues().spliterator(), false)
                    .filter(x -> x.getIssueStatus().getId() == 3)  // In Progress status
                    .collect(Collectors.toList());
            double currentLoad = currentIssues.stream().mapToDouble(x -> this.getLoad(x)).sum();
            if (currentLoad < 0.95) {  // if load < 95%
                List<IssueEntity> futureIssues = StreamSupport.stream(user.getIssues().spliterator(), false)
                        .filter(x -> x.getIssueStatus().getId() == 10006)  // To Do status
                        .collect(Collectors.toList());

                if (futureIssues.size() > 0) {
                    List<Number[]> choiceCoefficients = futureIssues
                            .stream().map(x -> {
                                Date deadlineDate = x.getDueDate() != null ? x.getDueDate() : x.getSprint().getEndDate();
                                double k = Period.between(LocalDate.from(deadlineDate.toInstant()), currentLocalDate).getDays();   // currentDate / currentLocalDate проблема с final
                                Number[] res = {futureIssues.indexOf(x), k / x.getPriority().getId()};
                                return res;
                            }).collect(Collectors.toList());
                    OptionalDouble minCoefficient = choiceCoefficients.stream().mapToDouble(x -> (double)x[1]).min();
                    double firstCoefficient = (double)(choiceCoefficients.get(0)[1]);
                    OptionalInt index = choiceCoefficients.stream()
                            .filter(x -> (double)x[1] == minCoefficient.orElse(firstCoefficient))
                            .mapToInt(x->(int)x[0]).findFirst();
                    if (index.isPresent()) {
                        currentLoad += this.getLoad(futureIssues.get(index.getAsInt()));
                    }
                }
            }
            if (currentLoad > 0) {
                while (currentLoad > 1) {
                    // + учесть dueDate, хранить список выбранных задач
                    String day = currentDate.format(dateFormat);
                    if (!days.contains(day)) {
                        days.add(day);
                    }
                    if (currentLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        loadData.addLoad(day, 0);
                    } else {
                        loadData.addLoad(day, 1);
                        currentLoad -= 1;
                    }
                    currentDate = currentDate.plusDays(1);
                }
            }

            loadData.addLoad(currentDate.format(dateFormat), currentLoad);
            userLoads.add(loadData);
        }
        days.sort(Comparator.naturalOrder());
        return new UserLoadViewModel(userLoads, days) ;
    }

    private double getLoad(IssueEntity issue) {
        if (issue == null) {
            return 0;
        }
        return ((double)issue.getAggregatetimeoriginalestimate() - issue.getTimespent()) / (secondsInHour * 8);
    }
}
