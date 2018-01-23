package br.com.guerethes.forum.system.controller;

import br.com.guerethes.forum.architecture.controller.CrudController;
import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.system.model.Category;
import br.com.guerethes.forum.system.model.CommentPost;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/post")
public class PostController extends CrudController<Post, Long> {

    @Autowired
    PostService postService;

    @Override
    protected void populateForm(Post post, ModelMap model) {
        if ( post.getId() == 0 ) {
            post.setCategory(new Category());
            model.addAttribute(getEntityName(), post);
        }
    }

    @Override
    protected void populateList(Post post, ModelMap model) {
        model.addAttribute("postList", postService.findPostByUser(getUsuarioLogado().getEmail()));
    }

    @Override
    public ModelAndView save(Post post, BindingResult result, ModelMap model, ModelAndView modelAndView, String action) throws InstantiationException, IllegalAccessException {
        post.setCreated(new Date());
        post.setUser(getUsuarioLogado());
        return super.save(post, result, model, modelAndView, action);
    }

    @RequestMapping({"/show/{id}"})
    public ModelAndView show(@PathVariable("id") Long id, ModelMap model, ModelAndView modelAndView) throws InstantiationException, IllegalAccessException {
        CommentPost commentPost = new CommentPost();
        commentPost.setPost(new Post());
        model.addAttribute("comment", commentPost);
        super.show(id, model, modelAndView);
        User user = getUsuarioLogado();
        if ( user == null ) {
            modelAndView.setViewName("public/post");
            return modelAndView;
        }
        return modelAndView;

    }

}