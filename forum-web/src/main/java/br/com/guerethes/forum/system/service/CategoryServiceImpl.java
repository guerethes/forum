package br.com.guerethes.forum.system.service;

import br.com.guerethes.forum.architecture.service.CrudServiceImpl;
import br.com.guerethes.forum.system.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends CrudServiceImpl<Category, Long> implements CategoryService {


}
