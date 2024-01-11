package com.example.spring_security_fundamentals_006.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/demo1")
    public String authorizationTestOne(){
        return "demo one";
    }

    @RequestMapping("/demo2")
    public String authorizationTestTwo(){
        return "demo two";
    }

    @PostMapping ("/demo3")
    public String demoPost(){
        return "demo post";
    }
}
