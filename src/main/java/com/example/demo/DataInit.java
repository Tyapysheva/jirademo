package com.example.demo;

import com.example.demo.dataServices.*;
import com.example.demo.model.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
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
    private RoleDAO roleDAO;

    private IssueDataService issueDataService;
    private IssueTypeDataService issueTypeDataService;
    private IssueStatusDataService issueStatusDataService;
    private ProjectDataService projectDataService;
    private PriorityDataService priorityDataService;
    private UserDataService userDataService;
    private DashboardDataService dashboardDataService;
    private RoleDataService roleDataService;

    @Autowired
    public DataInit(IssueTypeDAO issueTypeDAO, IssueStatusDAO issueStatusDAO,
                    UserDAO userDAO, DashboardDAO dashboardDAO,
                    IssueDAO issueDAO, ProjectDAO projectDAO, PriorityDAO priorityDAO,
                    SprintDAO sprintDAO, RoleDAO roleDAO,
                    IssueDataService issueDataService, PriorityDataService priorityDataService,
                    IssueTypeDataService issueTypeDataService, IssueStatusDataService issueStatusDataService,
                    ProjectDataService projectDataService, UserDataService userDataService,
                    DashboardDataService dashboardDataService,
                    RoleDataService roleDataService) {
        this.issueDAO = issueDAO;
        this.issueTypeDAO = issueTypeDAO;
        this.issueStatusDAO = issueStatusDAO;
        this.projectDAO = projectDAO;
        this.priorityDAO = priorityDAO;
        this.userDAO = userDAO;
        this.dashboardDAO = dashboardDAO;
        this.sprintDAO = sprintDAO;
        this.roleDAO = roleDAO;

        this.issueDataService = issueDataService;
        this.issueTypeDataService = issueTypeDataService;
        this.issueStatusDataService = issueStatusDataService;
        this.projectDataService = projectDataService;
        this.priorityDataService = priorityDataService;
        this.userDataService = userDataService;
        this.dashboardDataService = dashboardDataService;
        this.roleDataService = roleDataService;
    }

    @Override
    public void run(ApplicationArguments args) {
        loadData();
    }

    public void loadData() {
        Iterable<IssueTypeEntity> types = issueTypeDataService.getAll();
        Iterable<IssueStatusEntity> statuses = issueStatusDataService.getAll();
        Iterable<ProjectEntity> projects = projectDataService.getAll();
        Iterable<PriorityEntity> priorities = priorityDataService.getAll();
        Iterable<DashboardEntity> dashboards = dashboardDataService.getAll();

        List<String> projectKeys = StreamSupport.stream(projects.spliterator(), false).map(p -> p.getKey()).collect(Collectors.toList());
        Iterable<IssueEntity> issues = issueDataService.getAll(projectKeys);
        Iterable<RoleEntity> roles = roleDataService.getAll(projectKeys);
        Iterable<UserEntity> users = userDataService.getAll();

        users = roleDataService.setUserRoles(users, roles, projectKeys);

        List<SprintEntity> sprints = StreamSupport.stream(issues.spliterator(), false)
                .map(x -> x.getSprint())
                .filter(x -> x != null)
                .distinct()
                .collect(Collectors.toList());

        this.issueTypeDAO.saveAll(types);
        this.issueStatusDAO.saveAll(statuses);
        this.projectDAO.saveAll(projects);
        this.priorityDAO.saveAll(priorities);
        this.dashboardDAO.saveAll(dashboards);
        this.roleDAO.saveAll(roles);
        this.userDAO.saveAll(users);

        List<Long> issueIds = StreamSupport.stream(issues.spliterator(), false)
                .map(x -> x.getId())
                .collect(Collectors.toList());
        issueIds.forEach(i -> {
            if (this.issueDAO.existsById(i)) {
                this.issueDAO.deleteById(i);
            }
        });
        List<Long> sprintIds = StreamSupport.stream(sprints.spliterator(), false)
                .map(x -> x.getId())
                .collect(Collectors.toList());
        sprintIds.forEach(i -> {
            if (this.sprintDAO.existsById(i)) {
                this.sprintDAO.deleteById(i);
            }
        });
        this.sprintDAO.saveAll(sprints);
        this.issueDAO.saveAll(issues);

//        try (InputStream inputStream = new FileInputStream("target/classes/application.properties")) {
//            Properties props = new Properties();
//            props.load(inputStream);
//            String message = props.getProperty("welcome.message");
//            props.setProperty("welcome.test", "test message");
//            OutputStream outputStream = new FileOutputStream("target/classes/application.properties");
//            props.store(outputStream, null);
//        }
//        catch (IOException ex) {
//            System.out.println("Alarm, error!");
//        }

    }
}
