package csh.back.post.post.controller;

import csh.back.global.rsData.RsData;
import csh.back.post.post.dto.PostDto;
import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name="PostController", description = "글 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class ApiV1PostController {
    private final PostService postService;

    @Operation(summary = "다건 조회")
    @GetMapping
    public List<PostDto> getItems() {
        List<Post> items = postService.findAll();
        return items
                .stream()
                .map(post -> new PostDto(post)) // PostDto로 변환
                .toList();
    }

    @Operation(summary = "단건 조회")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public PostDto getItem(
            @PathVariable int id
    ) {
        Post post = postService.findById(id).get();

        return new PostDto(post);
    }

    @Operation(summary = "글 삭제")
    @Transactional
    @DeleteMapping("/{id}")
    public RsData<PostDto> delete(@PathVariable int id) {
        Post post = postService.findById(id).get();

        postService.delete(post);


        return new RsData<>("200-1",
                "%d번 글이 삭제되었습니다.".formatted(post.getId()),new PostDto(post));
    }

    @Operation(summary = "글 등록")
    @Transactional
    @PostMapping("/create")
    public RsData<PostDto> write(@Valid @RequestBody PostWriteReqBody req) {
        Post p = postService.write(req.title(), req.content());
        long totalCnt = postService.count();
        PostDto dto = new PostDto(p);
//        PostWriteResBody resBody = new PostWriteResBody(dto, totalCnt);
        return new RsData<>("201-1", "%d번 글이 작성되었습니다.".formatted(p.getId()), dto);
    }

    @Operation(summary = "글 수정")
    @Transactional
    @PutMapping("/edit/{id}")
    public RsData<PostDto> edit(@PathVariable int id, @Valid @RequestBody PostWriteReqBody req) {
        Post p = postService.findById(id).get();
        postService.modify(p, req.title(), req.content());
        PostDto dto = new PostDto(p);
        return new RsData<>("201-1", "성공했어요~~!", dto);
    }

    record PostWriteReqBody(@NotBlank String title, @NotBlank String content){}
    record PostWriteResBody(@NotBlank PostDto post, @NotBlank long totalCount){}
}