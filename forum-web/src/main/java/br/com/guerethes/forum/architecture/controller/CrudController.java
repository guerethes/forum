package br.com.guerethes.forum.architecture.controller;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.architecture.service.CrudService;
import br.com.guerethes.forum.architecture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class CrudController<T extends Persistable<PK>, PK extends Serializable> {

    public static final String SUCCESS_MESSAGE_KEY = "success";
    public static final String ERROR_MESSAGE_KEY = "error";
    public static final String INFO_MESSAGE_KEY = "info";

    @Autowired
    protected CrudService<T, PK> service;

    @Autowired
    private UserService userService;

    public CrudController() {
    }

    @RequestMapping({"/"})
    public String index() {
        return "redirect:list";
    }

    @RequestMapping({"/create"})
    public ModelAndView create(T entity, ModelMap model) {
        ModelAndView modelAndView = new ModelAndView();
        this.populateForm(entity, model);
        modelAndView.setViewName("admin/" + this.getEntityName() + "/form");
        return modelAndView;
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    public ModelAndView save(T entity, BindingResult result, ModelMap model, ModelAndView modelAndView, @RequestParam(value="action", required=true) String action)
            throws InstantiationException, IllegalAccessException {

        if (action.equals("save")) {
            if(result.hasErrors()) {
                return create(entity, model);
            } else {
                this.service.saveOrUpdate(entity);
                addSuccessMsg(modelAndView,"Operação Realizada com Sucesso.");
                populateForm(entity, model);
            }

            return show(entity.getId(), model, modelAndView);
        } else {
            return list(entity, model);
        }
    }

    @RequestMapping({"/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") PK id, ModelMap model, RedirectAttributes redirect)
            throws IllegalAccessException, InstantiationException {

        ModelAndView modelAndView = new ModelAndView();
        Persistable entity = this.service.find(id);
        if(entity == null) {
            addErrorMsg(modelAndView,"Não foi possível encontrar a informção.");
            entity = getEntityClass().newInstance();
            return list((T) entity, model);
        } else {
            this.populateForm((T) entity, model);
            model.addAttribute(this.getEntityName(), entity);
            modelAndView.setViewName("admin/" + this.getEntityName() + "/form");
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST} )
    public ModelAndView update(T entity, BindingResult result, ModelMap model, RedirectAttributes redirect)
            throws InstantiationException, IllegalAccessException {

        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()) {
            this.populateForm(entity, model);
            model.addAttribute(this.getEntityName(), entity);
            modelAndView.setViewName(this.getEntityName() + "/edit");
            return edit(entity.getId(), model, redirect );
        } else {
            Persistable dbEntity = this.service.find(entity.getId());
            if(dbEntity == null) {
                addErrorMsg(modelAndView,"Não foi possível realzar a atualização.");
                modelAndView.setViewName(this.getEntityName() + "/list");
                return list(entity, model);
            } else {
                this.service.add(entity);
                addSuccessMsg(modelAndView,"Atualização realizada com sucesso.");
                return list(entity, model);
            }
        }
    }

    @RequestMapping({"/show/{id}"})
    public ModelAndView show(@PathVariable("id") PK id, ModelMap model, ModelAndView modelAndView)
            throws IllegalAccessException, InstantiationException {
        Persistable entity = this.service.find(id);
        if(entity == null) {
            addErrorMsg(modelAndView,"Não foi possível carregar as informações.");
            entity = getEntityClass().newInstance();
            return list((T) entity, model);
        } else {
            this.populateForm((T) entity, model);
            model.addAttribute(this.getEntityName(), entity);
            modelAndView.setViewName("admin/" + this.getEntityName() + "/show");
            return modelAndView;
        }
    }

    @RequestMapping({"/delete/{id}"})
    public ModelAndView delete(@PathVariable("id") PK id, ModelMap model, ModelAndView modelAndView)
            throws IllegalAccessException, InstantiationException {

        Persistable entity = this.service.find(id);
        if(entity == null) {
            addErrorMsg(modelAndView,"Não foi possível carregar as informações.");
            entity = getEntityClass().newInstance();
        } else {
            this.service.remove(entity);
            addSuccessMsg(modelAndView,"Informação removida com sucesso.");
        }

        return list((T) entity, model);
    }

    @RequestMapping({"/list"})
    public ModelAndView list(T entity, ModelMap model) {
        ModelAndView modelAndView = new ModelAndView();
        this.populateList(entity, model);
        modelAndView.setViewName("admin/" + this.getEntityName() + "/list");
        return modelAndView;
    }

    protected void populateForm(T entity, ModelMap model) {
        model.addAttribute(this.getEntityName(), entity);
        prePopulateForm(model, entity);
    }

    protected void prePopulateForm(ModelMap model, T entity){}

    protected void prePopulateList(ModelMap model){}

    protected void populateList(T entity, ModelMap model) {
        model.addAttribute(this.getEntityName() + "List", this.service.getAll());
        prePopulateList(model);
    }

    protected User getUsuarioLogado() {
        if ( SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String ) {
            return null;
        } else {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.findUserByEmail(user.getUsername());
        }
    }

    private Class<T> getEntityClass() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        return (Class)((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
    }

    protected String getEntityName() {
        return StringUtils.uncapitalize(this.getEntityClass().getSimpleName());
    }

    protected String getEntityName(Class clazz) {
        return StringUtils.uncapitalize(clazz.getSimpleName());
    }

    private String getBaseDir() {
        return this.getEntityName().toLowerCase();
    }

    protected void addSuccessMsg(ModelAndView modelAndView, String msg) {
        List<String> msgs = new ArrayList();
        if ( modelAndView.getModel().get(SUCCESS_MESSAGE_KEY) != null )
            msgs = (List<String>) modelAndView.getModel().get(SUCCESS_MESSAGE_KEY);
        msgs.add(msg);
        modelAndView.addObject(SUCCESS_MESSAGE_KEY, msgs);
    }

    protected void addInfoMsg(ModelAndView modelAndView, String msg) {
        List<String> msgs = new ArrayList();
        if ( modelAndView.getModel().get(INFO_MESSAGE_KEY) != null )
            msgs = (List<String>) modelAndView.getModel().get(INFO_MESSAGE_KEY);
        msgs.add(msg);
        modelAndView.addObject(INFO_MESSAGE_KEY, msgs);
    }

    protected void addErrorMsg(ModelAndView modelAndView, String msg) {
        List<String> msgs = new ArrayList();
        if ( modelAndView.getModel().get(ERROR_MESSAGE_KEY) != null )
            msgs = (List<String>) modelAndView.getModel().get(ERROR_MESSAGE_KEY);
        msgs.add(msg);
        modelAndView.addObject(ERROR_MESSAGE_KEY, msgs);
    }

}