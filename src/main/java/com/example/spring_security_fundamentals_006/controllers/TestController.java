package com.example.spring_security_fundamentals_006.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test1")
    public String authorizationTestOne(){
        return "test one";
    }

    @RequestMapping("/test2")
    public String authorizationTestTwo(){
        return "test two";
    }
}
