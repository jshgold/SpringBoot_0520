package csh.back.post.post.controller;

import csh.back.post.post.dto.PostDto;
import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping("")
    public List<PostDto> getItems() {
        List<Post> items = postService.findAll();
        return items
                .stream()
                .map(post -> new PostDto(post)) // PostDto로 변환
                .toList();
    }

    @Transactional
    @GetMapping("/{id}/delete")
    public PostDto delete(@PathVariable int id) {
        Post post = postService.findById(id).get();

        postService.delete(post);

        return new PostDto(post);
    }
}