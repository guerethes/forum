package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudService;
import br.com.guerethes.forum.system.model.Post;

import java.util.List;

public interface PostService extends CrudService<Post, Long>{

    List<Post> findPostByUser(String email);
    List<Post> findPostByThread(Long idPost);
    List<Post> findPostByAtive();

}