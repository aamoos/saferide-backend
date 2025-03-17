package com.saferide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "ok";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("여기타니?");
        return "ok";
    }


}
