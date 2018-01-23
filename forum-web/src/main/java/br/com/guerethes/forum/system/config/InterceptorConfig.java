package br.com.guerethes.forum.system.config;

import br.com.guerethes.forum.architecture.service.UserService;
import br.com.guerethes.forum.system.interceptor.GenericInterceptor;
import br.com.guerethes.forum.system.service.CategoryService;
import br.com.guerethes.forum.system.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GenericInterceptor(categoryService, userService, postService));
    }

}