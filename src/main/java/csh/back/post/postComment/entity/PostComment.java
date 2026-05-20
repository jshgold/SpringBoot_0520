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
    private String content;
    @ManyToOne
    @JsonIgnore
    private Post post;

    public PostComment(Post post, String content) {
        this.post = post;
        this.content = content;
    }

    public void modify(String content) {
        this.content = content;
    }

}