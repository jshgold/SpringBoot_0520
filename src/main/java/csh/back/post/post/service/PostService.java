package csh.back.post.post.service;

import csh.back.post.post.entity.Post;
import csh.back.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post write(String title, String content) {
        return postRepository.save(new Post(title, content));
    }

    public int count() {
        return (int) postRepository.count();
    }
}
