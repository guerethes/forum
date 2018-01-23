package br.com.guerethes.forum.system.controller;

import br.com.guerethes.forum.architecture.controller.CrudController;
import br.com.guerethes.forum.architecture.model.Role;
import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.architecture.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends CrudController<User, Long> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    public ModelAndView save(User user, BindingResult result, ModelMap model, ModelAndView modelAndView,
                             @RequestParam(value = "action", required = true) String action) throws InstantiationException, IllegalAccessException {

        //Criptografar senha.
        if ( user.getId() == null )
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        return super.save(user, result, model, modelAndView, action);
    }
}