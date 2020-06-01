package com.example.demo.controllers;

import com.example.demo.dataServices.IssueDataService;
import com.example.demo.dataServices.UserDataService;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositories.IssueDAO;
import com.example.demo.repositories.UserDAO;
import com.example.demo.viewModels.UserLoadModel;
import com.example.demo.viewModels.UserLoadViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.StreamSupport;

@Controller
public class MainController {
    private IssueDAO issueDAO;
    private UserDAO userDAO;
    private UserDataService userDataService;

    @Autowired
    public MainController(IssueDAO issueDAO, UserDAO userDAO, UserDataService userDataService) {
        this.issueDAO = issueDAO;
        this.userDAO = userDAO;
        this.userDataService = userDataService;
    }

    @GetMapping("/")
    public String index(Model m) {
        Iterable<UserEntity> users = userDAO.findAll();
        UserLoadViewModel userLoadData = this.userDataService.prepare(users);
        m.addAttribute("days", userLoadData.days);
        m.addAttribute("userLoads", userLoadData.userLoads);
        return "index";
    }

    @GetMapping("/issues")
    public String issues(Model m) {
        Iterable<IssueEntity> entities = issueDAO.findAll();
        m.addAttribute("issues", entities);
        m.addAttribute("issueCount", ((Collection<?>)entities).size());
        return "issues";
    }

//    @ResponseBody
//    @RequestMapping("/issues")
//    public Iterable<IssueEntity> issues() {
//        //Iterable<IssueEntity> issues = issueDataService.getAll();
//        Iterable<IssueEntity> entities = issueDAO.findAll();
//        return entities;
//    }
//
//    @ResponseBody
//    @RequestMapping("/issues/types")
//    public Iterable<IssueTypeEntity> issueTypes() {
//        Iterable<IssueTypeEntity> entities = issueTypeDAO.findAll();
//        return entities;
//    }
//
//    @ResponseBody
//    @RequestMapping("/issues/statuses")
//    public Iterable<IssueStatusEntity> issueStatuses() {
//        Iterable<IssueStatusEntity> entities = issueStatusDAO.findAll();
//        return entities;
//    }
}
