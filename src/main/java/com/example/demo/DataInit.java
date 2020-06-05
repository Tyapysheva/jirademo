package com.example.demo;

import com.example.demo.dataServices.*;
import com.example.demo.model.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DataInit implements ApplicationRunner {
    private IssueTypeDAO issueTypeDAO;
    private IssueStatusDAO issueStatusDAO;
    private IssueDAO issueDAO;
    private ProjectDAO projectDAO;
    private PriorityDAO priorityDAO;
    private UserDAO userDAO;
    private DashboardDAO dashboardDAO;
    private SprintDAO sprintDAO;

    private IssueDataService issueDataService;
    private IssueTypeDataService issueTypeDataService;
    private IssueStatusDataService issueStatusDataService;
    private ProjectDataService projectDataService;
    private PriorityDataService priorityDataService;
    private UserDataService userDataService;
    private DashboardDataService dashboardDataService;

    @Autowired
    public DataInit(IssueTypeDAO issueTypeDAO, IssueStatusDAO issueStatusDAO,
                    UserDAO userDAO,DashboardDAO dashboardDAO,
                    IssueDAO issueDAO,ProjectDAO projectDAO, PriorityDAO priorityDAO,
                    IssueDataService issueDataService,PriorityDataService priorityDataService,
                    IssueTypeDataService issueTypeDataService, IssueStatusDataService issueStatusDataService,
                    ProjectDataService projectDataService,UserDataService userDataService,
                    DashboardDataService dashboardDataService, SprintDAO sprintDAO) {
        this.issueDAO = issueDAO;
        this.issueTypeDAO = issueTypeDAO;
        this.issueStatusDAO = issueStatusDAO;
        this.projectDAO = projectDAO;
        this.priorityDAO = priorityDAO;
        this.userDAO = userDAO;
        this.dashboardDAO = dashboardDAO;
        this.sprintDAO = sprintDAO;

        this.issueDataService = issueDataService;
        this.issueTypeDataService = issueTypeDataService;
        this.issueStatusDataService = issueStatusDataService;
        this.projectDataService = projectDataService;
        this.priorityDataService = priorityDataService;
        this.userDataService = userDataService;
        this.dashboardDataService = dashboardDataService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadData();
    }

    public void loadData() {
        Iterable<IssueTypeEntity> types = issueTypeDataService.getAll();
        Iterable<IssueStatusEntity> statuses = issueStatusDataService.getAll();
        Iterable<ProjectEntity> projects = projectDataService.getAll();
        Iterable<PriorityEntity> priorities = priorityDataService.getAll();
        Iterable<UserEntity> users = userDataService.getAll();
        Iterable<DashboardEntity> dashboards = dashboardDataService.getAll();

        List<String> projectKeys = StreamSupport.stream(projects.spliterator(), false).map(p -> p.getKey()).collect(Collectors.toList());
        Iterable<IssueEntity> issues = issueDataService.getAll(projectKeys);

        List<SprintEntity> sprints = StreamSupport.stream(issues.spliterator(), false)
                .map(x -> x.getSprint())
                .distinct()
                .collect(Collectors.toList());

        this.issueTypeDAO.saveAll(types);
        this.issueStatusDAO.saveAll(statuses);
        this.projectDAO.saveAll(projects);
        this.priorityDAO.saveAll(priorities);
        this.dashboardDAO.saveAll(dashboards);
        this.userDAO.saveAll(users);
        this.sprintDAO.saveAll(sprints);

        this.issueDAO.saveAll(issues);
    }
}
