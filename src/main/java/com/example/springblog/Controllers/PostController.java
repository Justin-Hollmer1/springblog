package com.example.springblog.Controllers;
import com.example.springblog.EmailService;
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

    private final UserRepository userRepository;


    private final PostRepository postRepository;
    private EmailService emailService;

    public PostController(PostRepository postRepository, UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.emailService = emailService;
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
    public String getPostByID(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

//    @PostMapping("/posts/create")
//    public String createPost(@RequestParam String title, @RequestParam String body, User user) {
//        System.out.println("");
//        Post post = new Post();
//        post.setTitle(title);
//        post.setBody(body);
//        post.setUser(user);
//        postRepository.save(post);
//        return "redirect:/posts";
//    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {
        User user = userRepository.getReferenceById(1L);
        post.setUser(user);
        postRepository.save(post);
        emailService.prepareAndSend(post, "Post Created", "This is the body of the post Title: " + post.getTitle());
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostById(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepository.getReferenceById(id));
        System.out.println();
        return "posts/editPost";
    }

    @PostMapping("/posts/edit")
    public String editPostById(@ModelAttribute Post post) {
        User user = userRepository.getReferenceById(1L);
        System.out.println("Post id: " + post.getId());
        post.setUser(user);
        postRepository.save(post);
        return "redirect:/posts";
    }

}
