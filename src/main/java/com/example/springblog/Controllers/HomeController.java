package com.example.springblog.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String defaultMapping() {
        return "This is the landing page!";
    }

    @GetMapping("/home")
    public String welcome() {
        return "home";
    }
}
