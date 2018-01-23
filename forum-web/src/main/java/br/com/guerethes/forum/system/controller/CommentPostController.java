package br.com.guerethes.forum.system.controller;

import br.com.guerethes.forum.architecture.controller.CrudController;
import br.com.guerethes.forum.system.model.CommentPost;
import br.com.guerethes.forum.system.service.CommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentPostController extends CrudController<CommentPost, Long> {

    @Autowired
    PostController postController;

    @Autowired
    CommentPostService commentPostService;

    @Override
    public ModelAndView save(CommentPost commentPost, BindingResult result, ModelMap model, ModelAndView modelAndView, String action) throws InstantiationException, IllegalAccessException {
        if ( commentPost.getId() == null ) {
            commentPost.setCreated(new Date());
            commentPost.setUser(getUsuarioLogado());
        }

        return super.save(commentPost, result, model, modelAndView, action);
    }

    @RequestMapping({"/show/{id}"})
    public ModelAndView show(@PathVariable("id") Long id, ModelMap model, ModelAndView modelAndView) throws IllegalAccessException, InstantiationException {
        CommentPost commentPost = commentPostService.find(id);
        if ( commentPost != null )
            return postController.show(commentPost.getPost().getId(), model, modelAndView);
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }

}