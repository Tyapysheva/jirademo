package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ExceptionHandlingController {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        System.out.format("Request: %s, raised %s", req.getRequestURL(), ex);

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", ex.getMessage());
        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error");
        return mav;
    }
}
