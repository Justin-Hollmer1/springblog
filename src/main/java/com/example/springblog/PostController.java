package com.example.springblog;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("Title 1", "Body 1"));
        postList.add(new Post("Title 2", "Body 2"));
        model.addAttribute("postTitle1", postList.get(0).getTitle());
        model.addAttribute("postBody1", postList.get(0).getBody());
        model.addAttribute("postTitle2", postList.get(1).getTitle());
        model.addAttribute("postBody2", postList.get(1).getBody());
        return "/show";
    }

    @GetMapping("/posts/{id}")
    public String getPostByID(@PathVariable int id, Model model) {
        Post post = new Post();
        post.setTitle("This is the post title: " + id);
        post.setBody("This is the post body: " + id);
        model.addAttribute("postTitle", post.getTitle());
        model.addAttribute("postBody", post.getBody());
        return "/index";
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
