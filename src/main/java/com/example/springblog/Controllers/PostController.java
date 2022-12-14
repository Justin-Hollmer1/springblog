package com.example.springblog.Controllers;
import com.example.springblog.Services.EmailService;
import com.example.springblog.Repos.PostRepository;
import com.example.springblog.Repos.UserRepository;
import com.example.springblog.models.Post;
import com.example.springblog.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String getPostByID(@PathVariable long id, Model model) {
        Post post = new Post();
//        post.setTitle("This is the post title: " + id);
//        post.setBody("This is the post body: " + id);
//        model.addAttribute("postTitle", post.getTitle());
//        model.addAttribute("postBody", post.getBody());
//        model.addAttribute("postUserEmail", post.getUser().getEmail());
        model.addAttribute("post", postRepository.getReferenceById(id));
        return "/individualPost";
    }

    @GetMapping("/posts/create")
    public String getPostByID(Model model) {
        model.addAttribute("post", new Post());
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser.getUsername() == null) {
            return "redirect:/login";
        }
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(currentUser);
        postRepository.save(post);
        emailService.prepareAndSend(post, "Post Created", "This is the body of the post Title: " + post.getTitle());
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostById(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepository.getReferenceById(id));
        System.out.println();
        return "/posts/editPost";
    }

    @PostMapping("/posts/edit")
    public String editPostById(@ModelAttribute Post post) {
        User user = userRepository.getReferenceById(1L);
        System.out.println("Post id: " + post.getId());
        post.setUser(user);
        postRepository.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        Post postToDelete = postRepository.getReferenceById(id);
        postRepository.delete(postToDelete);
        return "redirect:/posts";
    }

}
