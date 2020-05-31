package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/test")
@Controller
public class TestController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.addAttribute("msg", "One Two / One Two!");
        return "test";
    }
}
