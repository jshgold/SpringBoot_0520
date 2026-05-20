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
    private String comment;
    @ManyToOne
    @JsonIgnore
    private Post post;

    public PostComment(Post post, String comment) {
        this.post = post;
        this.comment = comment;
    }

    public void modify(String comment) {
        this.comment = comment;
    }

}