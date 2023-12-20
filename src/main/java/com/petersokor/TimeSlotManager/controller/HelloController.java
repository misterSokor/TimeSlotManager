package com.petersokor.TimeSlotManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/Hello")
    public String helloPage() {
        return "Hello"; //returns Thymeleaf page Hello
    }
}
