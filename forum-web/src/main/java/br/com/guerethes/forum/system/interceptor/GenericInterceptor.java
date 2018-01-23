package br.com.guerethes.forum.system.interceptor;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.architecture.service.UserService;
import br.com.guerethes.forum.system.model.Category;
import br.com.guerethes.forum.system.model.Post;
import br.com.guerethes.forum.system.model.dto.SearchPost;
import br.com.guerethes.forum.system.service.CategoryService;
import br.com.guerethes.forum.system.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GenericInterceptor extends HandlerInterceptorAdapter {

    private CategoryService categoryService;
    private UserService userService;
    private PostService postService;

    public GenericInterceptor(CategoryService categoryService, UserService userService, PostService postService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  {

        List<Post> postList = postService.getAll();
        modelAndView.addObject("posts", postList);
        modelAndView.addObject("categories", categoryService.getAll());
        modelAndView.addObject("search", new SearchPost());
        if ( getUsuarioLogado() != null ) {
            modelAndView.addObject("user", getUsuarioLogado());
        }
        System.out.println("\n-------- AdminInterceptor.postHandle --- ");
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