package csh.back.post.post.dto;

import csh.back.post.post.entity.Post;
import csh.back.post.postComment.entity.PostComment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
        int id,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        String subject,
        String body,
        List<PostComment> postComment
) {

    public PostDto(Post post) {
        this(
                post.getId(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getTitle(),
                post.getContent(),
                post.getComments()
        );
    }
}