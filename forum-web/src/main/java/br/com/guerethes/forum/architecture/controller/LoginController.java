package br.com.guerethes.forum.architecture.controller;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.architecture.service.UserService;
import br.com.guerethes.forum.system.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	PostService postService;

	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(ModelMap model){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/index");
		return modelAndView;
	}

}