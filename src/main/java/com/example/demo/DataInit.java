package com.example.demo;

import com.example.demo.dataServices.IssueDataService;
import com.example.demo.dataServices.IssueStatusDataService;
import com.example.demo.dataServices.IssueTypeDataService;
import com.example.demo.dataServices.PriorityDataService;
import com.example.demo.dataServices.ProjectDataService;
import com.example.demo.dataServices.UserDataService;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.IssueStatusEntity;
import com.example.demo.model.IssueTypeEntity;
import com.example.demo.model.PriorityEntity;
import com.example.demo.model.ProjectEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositories.IssueDAO;
import com.example.demo.repositories.IssueStatusDAO;
import com.example.demo.repositories.IssueTypeDAO;
import com.example.demo.repositories.PriorityDAO;
import com.example.demo.repositories.ProjectDAO;
import com.example.demo.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {
    private IssueTypeDAO issueTypeDAO;
    private IssueStatusDAO issueStatusDAO;
    private IssueDAO issueDAO;
    private ProjectDAO projectDAO;
    private PriorityDAO priorityDAO;
    private UserDAO userDAO;

    private IssueDataService issueDataService;
    private IssueTypeDataService issueTypeDataService;
    private IssueStatusDataService issueStatusDataService;
    private ProjectDataService projectDataService;
    private PriorityDataService priorityDataService;
    private UserDataService userDataService;

    @Autowired
    public DataInit(IssueTypeDAO issueTypeDAO, IssueStatusDAO issueStatusDAO,UserDAO userDAO,
                    IssueDAO issueDAO,ProjectDAO projectDAO, PriorityDAO priorityDAO,
                    IssueDataService issueDataService,PriorityDataService priorityDataService,
                    IssueTypeDataService issueTypeDataService, IssueStatusDataService issueStatusDataService,
                    ProjectDataService projectDataService,UserDataService userDataService) {
        this.issueDAO = issueDAO;
        this.issueTypeDAO = issueTypeDAO;
        this.issueStatusDAO = issueStatusDAO;
        this.projectDAO = projectDAO;
        this.priorityDAO = priorityDAO;
        this.userDAO =userDAO;
        this.issueDataService = issueDataService;
        this.issueTypeDataService = issueTypeDataService;
        this.issueStatusDataService = issueStatusDataService;
        this.projectDataService = projectDataService;
        this.priorityDataService = priorityDataService;
        this.userDataService = userDataService;

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterable<IssueTypeEntity> types = issueTypeDataService.getAll();
        Iterable<IssueStatusEntity> statuses = issueStatusDataService.getAll();
        Iterable<IssueEntity> issues = issueDataService.getAll();
        Iterable<ProjectEntity> projects = projectDataService.getAll();
        Iterable<PriorityEntity> priorities = priorityDataService.getAll();
        Iterable<UserEntity> users = userDataService.getAll();

        this.issueTypeDAO.saveAll(types);
        this.issueStatusDAO.saveAll(statuses);
        this.projectDAO.saveAll(projects);
        this.priorityDAO.saveAll(priorities);
        this.userDAO.saveAll(users);


//        this.issueDAO.saveAll(issues);
    }
}
