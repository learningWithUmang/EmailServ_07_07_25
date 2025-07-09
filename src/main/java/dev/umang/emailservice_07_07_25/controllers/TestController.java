package dev.umang.emailservice_07_07_25.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /*
    localhost:8080/test
     */
    @GetMapping("/test")
    public String test() {
        System.out.println("Running TestController inside email service");
        return "Email Service is running!";
    }
}
