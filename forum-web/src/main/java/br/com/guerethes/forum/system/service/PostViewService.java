package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudService;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.PostView;

import java.util.List;

public interface PostViewService extends CrudService<PostView, Long>{

    void addView(Long idPost);

}