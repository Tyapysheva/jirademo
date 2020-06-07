package com.example.demo.dataServices;


import com.example.demo.mapperClass.User;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.viewModels.IssueLoadModel;
import com.example.demo.viewModels.UserLoadModel;
import com.example.demo.viewModels.UserLoadViewModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserDataService {
    @Autowired
    private ModelMapper modelMapper;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM E");
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
            List<IssueLoadModel> currentLoad = currentIssues.stream().map(x -> new IssueLoadModel(x, this.getLoad(x))).collect(Collectors.toList());
            if (IssueLoadModel.getSumLoad(currentLoad) < 0.95) {  // if load < 95%
                List<IssueEntity> futureIssues = StreamSupport.stream(user.getIssues().spliterator(), false)
                        .filter(x -> x.getIssueStatus().getId() == 10006)  // To Do status
                        .collect(Collectors.toList());

                if (futureIssues.size() > 0) {
                    List<Number[]> choiceCoefficients = futureIssues
                            .stream().map(x -> {
                                Date deadlineDate = x.getDueDate() != null ? x.getDueDate() : x.getSprint().getEndDate();
                                ZonedDateTime deadlineDateTime = ZonedDateTime.ofInstant(deadlineDate.toInstant(), ZoneId.systemDefault());
                                double k = Period.between(currentLocalDate, deadlineDateTime.toLocalDate()).getDays();   // currentDate / currentLocalDate проблема с final
                                Number[] res = {futureIssues.indexOf(x), k / x.getPriority().getId()};
                                return res;
                            }).collect(Collectors.toList());
                    OptionalDouble minCoefficient = choiceCoefficients.stream().mapToDouble(x -> (double)x[1]).min();
                    double firstCoefficient = (double)(choiceCoefficients.get(0)[1]);
                    OptionalInt index = choiceCoefficients.stream()
                            .filter(x -> (double)x[1] == minCoefficient.orElse(firstCoefficient))
                            .mapToInt(x->(int)x[0]).findFirst();
                    if (index.isPresent()) {
                        IssueEntity selectedIssue = futureIssues.get(index.getAsInt());
                        currentLoad.add(new IssueLoadModel(selectedIssue, this.getLoad(selectedIssue)));
                    }
                }
            }

            if (IssueLoadModel.getSumLoad(currentLoad) > 0) {
                for (IssueLoadModel load : currentLoad) {
                    String day = this.getDateString(days, currentDate);
                    Date deadlineDate = load.issue.getDueDate() != null ? load.issue.getDueDate() : load.issue.getSprint().getEndDate();
                    ZonedDateTime dueDate = ZonedDateTime.ofInstant(deadlineDate.toInstant(), ZoneId.systemDefault());
                    double daysToDeadline = Period.between(currentLocalDate, dueDate.toLocalDate()).getDays();

                    if (daysToDeadline == 0) {  // если сегодня - день дедлайна по задаче, то оставшуюся нагрузку закидываем на сегодняшний день
                        this.addLoadInfo(loadData, currentDate, load);
                    }
                    else {
                        while (loadData.getLoad(day) + load.load > 1) {
                            IssueLoadModel remainingLoad = new IssueLoadModel(load.issue, 1 - loadData.getLoad(day));
                            load.removeLoad(1 - loadData.getLoad(day));
                            this.addLoadInfo(loadData, currentDate, remainingLoad);

                            currentDate = currentDate.plusDays(1);
                            day = this.getDateString(days, currentDate);
                        }
                        this.addLoadInfo(loadData, currentDate, load);
                    }
                }
                currentDate = currentDate.plusDays(1);
            }

            userLoads.add(loadData);
            currentDate = currentLocalDate;
        }
        days.sort(Comparator.naturalOrder());

        for (UserLoadModel userLoad : userLoads) {
            for (String day : days) {
                if (!userLoad.load.containsKey(day)) {
                    userLoad.addLoad(day);
                }
            }
        }

        return new UserLoadViewModel(userLoads, days) ;
    }

    private String getDateString(List<String> days, LocalDate currentDate) {
        String day = currentDate.format(dateFormat);
        if (!days.contains(day)) {
            days.add(day);
        }
        return day;
    }

    private void addLoadInfo(UserLoadModel loadData, LocalDate currentDate, IssueLoadModel load) {
        String day = currentDate.format(dateFormat);
        boolean isAdded = false;
        do {
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                loadData.addLoad(day);
                currentDate.plusDays(1);
            } else {
                loadData.addLoad(currentDate.format(dateFormat), load);
                isAdded = true;
            }
        } while (!isAdded);
    }

    private double getLoad(IssueEntity issue) {
        if (issue == null) {
            return 0;
        }
        return ((double)issue.getAggregatetimeoriginalestimate() - issue.getTimespent()) / (secondsInHour * 8);
    }
}
