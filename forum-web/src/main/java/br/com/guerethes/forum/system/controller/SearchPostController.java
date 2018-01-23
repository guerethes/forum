package br.com.guerethes.forum.system.controller;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.architecture.service.UserService;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.dto.SearchPost;
import br.com.guerethes.forum.system.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/postSearch")
public class SearchPostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @RequestMapping({"/search"})
    public ModelAndView showPublic(SearchPost searchPost, ModelMap model, ModelAndView modelAndView) {
        List<Post> postList = postService.findByQuery(searchPost);
        if ( postList != null && postList.isEmpty() )
            postList = null;

        model.addAttribute("postList", postList);

        if ( getUsuarioLogado() == null ) {
            modelAndView.setViewName("/public/search");
        } else {
            modelAndView.setViewName("/admin/search");
        }

        return modelAndView;
    }

    protected User getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( principal != null ) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
                return null;
            } else {
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return userService.findUserByEmail(user.getUsername());
            }
        }
        return null;
    }

}