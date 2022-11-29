package com.example.springblog.Repos;

import com.example.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String postTitle);
}
