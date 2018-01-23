package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudServiceImpl;
import br.com.guerethes.forum.system.model.CommentPost;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.dto.SearchPost;
import br.com.guerethes.forum.system.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentPostServiceImpl extends CrudServiceImpl<CommentPost, Long> implements CommentPostService {


}
