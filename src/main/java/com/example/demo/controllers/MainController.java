package com.example.demo.controllers;

import com.example.demo.dataServices.IssueDataService;
import com.example.demo.model.IssueEntity;
import com.example.demo.repositories.IssueDAO;
import com.example.demo.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class MainController {
    private IssueDAO issueDAO;
    private UserDAO userDAO;

    @Autowired
    public MainController(IssueDAO issueDAO, UserDAO userDAO) {
        this.issueDAO = issueDAO;
    }

    @GetMapping("/")
    public String index(Model m) {
        Iterable<IssueEntity> entities = issueDAO.findAll();
        m.addAttribute("issues", entities);
        m.addAttribute("issueCount", ((Collection<?>)entities).size());
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
