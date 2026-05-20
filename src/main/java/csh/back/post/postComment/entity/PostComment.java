package csh.back.post.postComment.entity;

import csh.back.global.jpa.entity.BaseEntity;
import csh.back.post.post.entity.Post;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class PostComment extends BaseEntity {
    private String comment;
    @ManyToOne
    private Post post;

    public PostComment(Post post, String comment) {
        this.post = post;
        this.comment = comment;
    }

}