package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudService;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.dto.SearchPost;

import java.util.List;

public interface PostService extends CrudService<Post, Long>{

    List<Post> findPostByUser(String email);
    List<Post> findByQuery(SearchPost searchPost);
}