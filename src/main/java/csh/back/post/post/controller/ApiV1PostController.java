package csh.back.post.post.controller;

import csh.back.global.rsData.RsData;
import csh.back.post.post.dto.PostDto;
import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping
    public List<PostDto> getItems() {
        List<Post> items = postService.findAll();
        return items
                .stream()
                .map(post -> new PostDto(post)) // PostDto로 변환
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getItem(
            @PathVariable int id
    ) {
        Post post = postService.findById(id).get();

        return new PostDto(post);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public RsData<PostDto> delete(@PathVariable int id) {
        Post post = postService.findById(id).get();

        postService.delete(post);


        return new RsData<>("200-1",
                "%d번 글이 삭제되었습니다.".formatted(post.getId()),new PostDto(post));
    }

    @Transactional
    @PostMapping("/create")
    public RsData<PostDto> create(@Valid @RequestBody writeForm form) {
        System.out.println(form.toString());
        Post p = postService.write(form.title(), form.content());
        PostDto dto = new PostDto(p);
        return new RsData<>("200-1", "성공했어요~~!", dto);
    }

    record writeForm(@NotBlank String title, @NotBlank String content){}
}