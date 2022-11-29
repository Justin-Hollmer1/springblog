package com.example.springblog;


import com.example.springblog.Repos.PostRepository;
import com.example.springblog.Repos.UserRepository;
import com.example.springblog.models.Post;
import com.example.springblog.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBlogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTest {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {
        testUser = userRepository.findByUsername("testUser");

        if (testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userRepository.save(newUser);
        }

        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    @Test
    public void contextLoads() {
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        assertNotNull(httpSession);
    }

    @Test
    public void testCreatePost() throws Exception {
        this.mvc.perform(post("/posts/create").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "test post")
                .param("body", "this is a test"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testShowPost() throws Exception {
        Post existingPost = postRepository.findAll().get(0);

        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(existingPost.getBody())));
    }


    @Test
    public void testPostIndex() throws Exception {
        Post existingPost = postRepository.findAll().get(0);


        this.mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Viewing all posts")))
                .andExpect(content().string(containsString(existingPost.getBody())));
    }

    @Test
    public void testEditPost() throws Exception {
        Post existingPost = postRepository.findAll().get(0);

        this.mvc.perform(post("/posts/edit").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "new title")
                .param("body", "new body"))
                .andExpect(status().is3xxRedirection());

        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("new title")))
                .andExpect(content().string(containsString("new body")));
    }

    @Test
    public void testDeletePost() throws Exception {
        this.mvc.perform(post("/posts/create").with(csrf())
                .param("title", "this will be deleted")
                .param("body", "this will also be deleted"))
                .andExpect(status().is3xxRedirection());

        Post existingPost = postRepository.findByTitle("this will be deleted");

        this.mvc.perform(post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());
    }

}
