package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudServiceImpl;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.PostView;
import br.com.guerethes.forum.system.repository.PostRepository;
import br.com.guerethes.forum.system.repository.PostViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostViewServiceImpl extends CrudServiceImpl<PostView, Long> implements PostViewService {

    @Autowired
    private PostViewRepository postViewRepository;

    public void addView(Long idPost) {
        postViewRepository.addView(idPost);
    }

}