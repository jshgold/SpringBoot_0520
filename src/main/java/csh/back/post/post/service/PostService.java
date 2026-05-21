package csh.back.post.post.service;

import csh.back.post.post.entity.Post;
import csh.back.post.post.repository.PostRepository;
import csh.back.post.postComment.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public long count() {
        return postRepository.count();
    }

    public Post write(String title, String content) {
        Post post = new Post(title, content);

        return postRepository.save(post);
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public Optional<Post> findLatest() {
        return postRepository.findFirstByOrderByIdDesc();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void modify(Post post, String title, String content) {
        post.modify(title, content);
    }

    public void writeComment(Post post, String content) {
        post.addComment(content);
    }

    public boolean deleteComment(Post post, PostComment postComment) {
        return post.deleteComment(postComment);
    }

    public void modifyComment(PostComment postComment, String content) {
        postComment.modify(content);
    }


    public void delete(Post post) {
        postRepository.delete(post);
    }
}
