package csh.back.post.post.controller;

import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class ApiV1Controller {
    private final PostService postService;

    @GetMapping("")
    public List<Post> getItems() {
        List<Post> items = postService.findAll();

        return items;
    }
}