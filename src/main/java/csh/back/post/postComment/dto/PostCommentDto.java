package csh.back.post.postComment.dto;

import csh.back.post.postComment.entity.PostComment;
import java.time.LocalDateTime;

public record PostCommentDto(
        int id,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        String content
) {

    public PostCommentDto(PostComment comment) {
        this(
                comment.getId(),
                comment.getCreateDate(),
                comment.getModifyDate(),
                comment.getContent()
        );
    }
}