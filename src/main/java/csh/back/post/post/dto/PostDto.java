package csh.back.post.post.dto;

import csh.back.post.post.entity.Post;

import java.time.LocalDateTime;

public record PostDto(
        int id,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        String subject,
        String body
) {

    public PostDto(Post post) {
        this(
                post.getId(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getTitle(),
                post.getContent()
        );
    }
}