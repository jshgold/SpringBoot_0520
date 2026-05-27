package csh.back.post.postComment.controller;

import csh.back.global.rsData.RsData;
import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import csh.back.post.postComment.dto.PostCommentDto;
import csh.back.post.postComment.entity.PostComment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="PostCommentController", description = "댓글 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
@RestController
public class ApiV1PostCommentController {
    private final PostService postService;

    @Operation(summary = "다건 조회")
    @Transactional(readOnly = true)
    @GetMapping
    public List<PostCommentDto> getItems(
            @PathVariable int postId
    ) {
        Post post = postService.findById(postId).get();

        return post
                .getComments()
                .stream()
                .map(PostCommentDto::new)
                .toList();
    }

    @Operation(summary = "단건 조회")
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public PostCommentDto getItem(
            @PathVariable int postId,
            @PathVariable int id
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = post.findCommentById(id).get();
        PostCommentDto dto = new PostCommentDto(postComment);
        return dto;
    }

    @Operation(summary = "댓글삭제")
    @Transactional
    @DeleteMapping("/{id}")
    public RsData<Void> delete(
            @PathVariable int postId,
            @PathVariable int id
    ) {
        Post post = postService.findById(postId).get();
        PostComment postComment = post.findCommentById(id).get();

        postService.deleteComment(post, postComment);

        return new RsData<>(
                "200-1",
                "%d번 댓글이 삭제되었습니다.".formatted(postComment.getId())
        );
    }



    public record PostCommentModifyReqBody(
            @NotBlank
            @Size(min = 2, max = 100)
            String content
    ) {
    }

    @Operation(summary = "댓글수정")
    @Transactional
    @PutMapping("/{id}")
    public RsData<Void> modify(
            @PathVariable int postId,
            @PathVariable int id,
            @RequestBody @Valid PostCommentModifyReqBody reqBody
    ) {
        Post post = postService.findById(postId).get();
        PostComment postComment = post.findCommentById(id).get();

        postService.modifyComment(postComment, reqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 댓글이 수정되었습니다.".formatted(postComment.getId())
        );
    }


    public record PostCommentWriteReqBody(
            @NotBlank
            @Size(min = 2, max = 100)
            String content
    ) {
    }

    @Operation(summary = "댓글등록")
    @Transactional
    @PostMapping
    public RsData<PostCommentDto> write(
            @PathVariable int postId,
            @Valid @RequestBody PostCommentWriteReqBody reqBody
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = postService.writeComment(post, reqBody.content);

        postService.flush();

        return new RsData<>(
                "201-1",
                "%d번 댓글이 작성되었습니다.".formatted(postComment.getId()),
                new PostCommentDto(postComment)
        );
    }
}
