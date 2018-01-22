package br.com.guerethes.forum.system.controller;

import br.com.guerethes.forum.architecture.controller.CrudController;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.service.PostService;
import br.com.guerethes.forum.system.service.PostViewService;
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

    @Autowired
    PostViewService postViewService;

    @Override
    public ModelAndView list(Post entity, ModelMap model) {
        loadActiveThreads(model);
        return super.list(entity, model);
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

    @Override
    public ModelAndView create(Post entity, ModelMap model) {
        loadActiveThreads(model);
        return super.create(entity, model);
    }

    @RequestMapping({"/public/show/{id}"})
    public ModelAndView showPublic(@PathVariable("id") Long id, ModelMap model, ModelAndView modelAndView) throws InstantiationException, IllegalAccessException {
        postViewService.addView(id);
        super.show(id, model, modelAndView);
        modelAndView.setViewName("public/post");
        return modelAndView;
    }

    @RequestMapping({"/show/{id}"})
    public ModelAndView show(@PathVariable("id") Long id, ModelMap model, ModelAndView modelAndView) throws InstantiationException, IllegalAccessException {
        loadActiveThreads(model);
        postViewService.addView(id);
        return super.show(id, model, modelAndView);
    }

    @Override
    protected void prePopulateForm(ModelMap model, Post postBd) {
        Post post = new Post();
        post.setThread(new Post());
        model.addAttribute("thread", post);
        postBd.setPostThreadList( postService.findPostByThread(postBd.getId()) );
    }

    private void loadActiveThreads(ModelMap model) {
        if ( getUsuarioLogado() != null )
            model.addAttribute("activeThreads", postService.findPostByUser(getUsuarioLogado().getEmail()));
    }

}