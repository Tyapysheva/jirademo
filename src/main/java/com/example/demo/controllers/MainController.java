package com.example.demo.controllers;

import com.example.demo.DataInit;
import com.example.demo.dataServices.UserDataService;
import com.example.demo.model.IssueEntity;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositories.IssueDAO;
import com.example.demo.repositories.ProjectDAO;
import com.example.demo.repositories.UserDAO;
import com.example.demo.viewModels.UserLoadCompactModel;
import com.example.demo.viewModels.UserLoadViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class MainController {
    private IssueDAO issueDAO;
    private UserDAO userDAO;
    private ProjectDAO projectDAO;
    private UserDataService userDataService;
    private DataInit initializer;
    private String allProjectString = "Все проекты";
    private String allRolesString = "Все роли";

    @Autowired
    public MainController(IssueDAO issueDAO, UserDAO userDAO, ProjectDAO projectDAO,
                          DataInit initializer, UserDataService userDataService) {
        this.issueDAO = issueDAO;
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
        this.userDataService = userDataService;
        this.initializer = initializer;
    }

    @GetMapping("/")
    public String index(@RequestParam("project") Optional<String> projectKey, @RequestParam("role") Optional<Long> roleId, Model m) {
        Iterable<UserEntity> users = userDAO.findAll();
        List<UserEntity> requiredUsers = StreamSupport.stream(users.spliterator(), false)
                .filter(x -> "atlassian".equals(x.getAccountType()))
                .collect(Collectors.toList());

        List<String> projects = StreamSupport.stream(projectDAO.findAll().spliterator(), false)
                .map(x -> x.getKey())
                .collect(Collectors.toList());

        Map<Long, String> roles = requiredUsers.stream()
                .map(x -> x.getRoles())
                .flatMap(x -> StreamSupport.stream(x.spliterator(), false))
                .filter(x -> !"Administrators".equals(x.getName()))
                .collect(Collectors.toMap(RoleEntity::getId, RoleEntity::getName, (r1, r2) -> r1));
        projects.add(allProjectString);
        roles.put(Long.valueOf(0), allRolesString);

        if (projectKey.isPresent() && !projectKey.get().isEmpty() && !projectKey.get().equals(allProjectString)) {
            String projectKeyQuery = projectKey.get().toUpperCase();
            requiredUsers.forEach(x -> {
                List<IssueEntity> filteredIssues = StreamSupport.stream(x.getIssues().spliterator(), false)
                        .filter(y -> projectKeyQuery.equals(y.getProject().getKey().toUpperCase()))
                        .collect(Collectors.toList());
                x.setIssues(filteredIssues);
            });
        }

        if (roleId.isPresent() && roleId.get() != 0) {
            requiredUsers = requiredUsers.stream()
                    .filter(x -> x.hasRole(roleId.get()))
                    .collect(Collectors.toList());
        }

        UserLoadViewModel userLoadData = this.userDataService.prepare(requiredUsers);
        List<UserLoadCompactModel> userLoadCompactData = StreamSupport.stream(userLoadData.userLoads.spliterator(), false)
                .map(x -> new UserLoadCompactModel(x, userLoadData.days))
                .collect(Collectors.toList());

        m.addAttribute("days", userLoadData.days);
        m.addAttribute("userLoads", userLoadData.userLoads);
        m.addAttribute("userLoadCompactData", userLoadCompactData);
        m.addAttribute("roleMap", roles);
        m.addAttribute("projects", projects);
        m.addAttribute("selectedRole", roleId.isPresent() ? roleId.get() : 0);
        m.addAttribute("selectedProject", projectKey.isPresent() ? projectKey.get() : allProjectString);
        return "index";
    }

    @GetMapping("/issues")
    public String issues(Model m) {
        Iterable<IssueEntity> entities = issueDAO.findAll();
        m.addAttribute("issues", entities);
        return "issues";
    }

    @PostMapping("/refresh")
    public ModelAndView refreshData(@RequestParam("returnUrl") String returnUrl, Model m) {
        initializer.loadData();
        return new ModelAndView("redirect:" + returnUrl);
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
