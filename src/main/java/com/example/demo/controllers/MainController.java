package com.example.demo.controllers;

import com.example.demo.dataServices.IssueDataService;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.IssueStatusEntity;
import com.example.demo.model.IssueTypeEntity;
import com.example.demo.repositories.IssueDAO;
import com.example.demo.repositories.IssueStatusDAO;
import com.example.demo.repositories.IssueTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("")
@Controller
public class MainController {
    @Autowired
    private IssueDAO issueDAO;

    @Autowired
    private IssueTypeDAO issueTypeDAO;

    @Autowired
    private IssueStatusDAO issueStatusDAO;

    @Autowired
    private IssueDataService issueDataService;

    @ResponseBody
    @GetMapping("/")
    public String index(Model m) {
        Iterable<IssueEntity> entities = issueDAO.findAll();
        m.addAttribute("issues", entities);
        return "index";
    }

    @ResponseBody
    @RequestMapping("/issues")
    public Iterable<IssueEntity> issues() {
        //Iterable<IssueEntity> issues = issueDataService.getAll();
        Iterable<IssueEntity> entities = issueDAO.findAll();
        return entities;
    }

    @ResponseBody
    @RequestMapping("/issues/types")
    public Iterable<IssueTypeEntity> issueTypes() {
        Iterable<IssueTypeEntity> entities = issueTypeDAO.findAll();
        return entities;
    }

    @ResponseBody
    @RequestMapping("/issues/statuses")
    public Iterable<IssueStatusEntity> issueStatuses() {
        Iterable<IssueStatusEntity> entities = issueStatusDAO.findAll();
        return entities;
    }
}
