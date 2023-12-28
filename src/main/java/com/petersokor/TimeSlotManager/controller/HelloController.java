package com.petersokor.TimeSlotManager.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    private final Logger logger = LogManager.getLogger(HelloController.class);

    @GetMapping("/Hello")
    public String helloPage(HttpServletRequest request, Model model) {
        String userName = (String) request.getAttribute("userName");
        logger.info("USER NAME IS " + userName);

        model.addAttribute("userName", userName);
        return "Hello";
    }


}
