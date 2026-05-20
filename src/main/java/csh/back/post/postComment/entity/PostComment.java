package csh.back.post.postComment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import csh.back.global.jpa.entity.BaseEntity;
import csh.back.post.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class PostComment extends BaseEntity {
    private String body;
    @ManyToOne
    @JsonIgnore
    private Post post;

    public PostComment(Post post, String body) {
        this.post = post;
        this.body = body;
    }

    public void modify(String comment) {
        this.body = comment;
    }

}