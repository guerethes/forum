package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudServiceImpl;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.dto.SearchPost;
import br.com.guerethes.forum.system.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl extends CrudServiceImpl<Post, Long> implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public List<Post> findPostByUser(String email) {
        return postRepository.findPostByUser(email);
    }

    public List<Post> findByQuery(SearchPost searchPost) {
        return postRepository.findByQuery(searchPost);
    }

}
