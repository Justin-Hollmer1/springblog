package com.example.springblog.Controllers;
import com.example.springblog.Repos.PostRepository;
import com.example.springblog.Repos.UserRepository;
import com.example.springblog.models.Post;
import com.example.springblog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    UserRepository userRepository;


    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> postList = new ArrayList<>();
        model.addAttribute("posts", postRepository.findAll());
        return "/show";
    }

    @GetMapping("/posts/{id}")
    public String getPostByID(@PathVariable int id, Model model) {
        Post post = new Post();
        post.setTitle("This is the post title: " + id);
        post.setBody("This is the post body: " + id);
        model.addAttribute("postTitle", post.getTitle());
        model.addAttribute("postBody", post.getBody());
        model.addAttribute("postUserEmail", post.getUser().getEmail());
        return "/index";
    }

    @GetMapping("/posts/create")
    public String getPostByID() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam String title, @RequestParam String body, User user) {
        System.out.println("");
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);
        postRepository.save(post);
        return "redirect:/posts";
    }

}
