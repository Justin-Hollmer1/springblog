package com.example.springblog;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String getPosts() {
        return "This is the posts page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String getPostByID(@PathVariable int id) {
        return "This is the posts " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String getPostByID() {
        return "This is the page to create a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "New post created";
    }

}
