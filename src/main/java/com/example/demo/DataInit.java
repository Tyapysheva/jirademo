package com.example.demo;

import com.example.demo.dataServices.IssueDataService;
import com.example.demo.dataServices.IssueStatusDataService;
import com.example.demo.dataServices.IssueTypeDataService;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.IssueStatusEntity;
import com.example.demo.model.IssueTypeEntity;
import com.example.demo.repositories.IssueDAO;
import com.example.demo.repositories.IssueStatusDAO;
import com.example.demo.repositories.IssueTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {
    private IssueTypeDAO issueTypeDAO;
    private IssueStatusDAO issueStatusDAO;
    private IssueDAO issueDAO;

    private IssueDataService issueDataService;
    private IssueTypeDataService issueTypeDataService;
    private IssueStatusDataService issueStatusDataService;

    @Autowired
    public DataInit(IssueTypeDAO issueTypeDAO, IssueStatusDAO issueStatusDAO,
                    IssueDAO issueDAO, IssueDataService issueDataService,
                    IssueTypeDataService issueTypeDataService, IssueStatusDataService issueStatusDataService) {
        this.issueDAO = issueDAO;
        this.issueTypeDAO = issueTypeDAO;
        this.issueStatusDAO = issueStatusDAO;
        this.issueDataService = issueDataService;
        this.issueTypeDataService = issueTypeDataService;
        this.issueStatusDataService = issueStatusDataService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterable<IssueTypeEntity> types = issueTypeDataService.getAll();
        Iterable<IssueStatusEntity> statuses = issueStatusDataService.getAll();
        Iterable<IssueEntity> issues = issueDataService.getAll();

        this.issueTypeDAO.saveAll(types);
        this.issueStatusDAO.saveAll(statuses);
//        this.issueDAO.saveAll(issues);
    }
}
