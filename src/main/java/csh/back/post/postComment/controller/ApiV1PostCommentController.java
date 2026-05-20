package csh.back.post.postComment.controller;

import csh.back.global.rsData.RsData;
import csh.back.post.post.entity.Post;
import csh.back.post.post.service.PostService;
import csh.back.post.postComment.dto.PostCommentDto;
import csh.back.post.postComment.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class ApiV1PostCommentController {
    private final PostService postService;

    @GetMapping
    @Transactional(readOnly = true)
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

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PostCommentDto getItem(
            @PathVariable int postId,
            @PathVariable int id
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = post.findCommentById(id).get();

        return new PostCommentDto(postComment);
    }

    @GetMapping("/{id}/delete")
    @Transactional
    public RsData<PostCommentDto> delete(
            @PathVariable int postId,
            @PathVariable int id
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = post.findCommentById(id).get();

        postService.deleteComment(post, postComment);

        PostCommentDto commentDto = new PostCommentDto(postComment);

        RsData rsData = new RsData<>("200-1",
                "%d번 댓글이 삭제되었습니다.".formatted(postComment.getId()), commentDto);

        return rsData;
    }
}
