package br.com.guerethes.forum.architecture.service;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guerethes on 2017-03-18.
 */
public interface CrudService<T, ID extends Serializable> {

    void add(Persistable entity) ;
    void saveOrUpdate(Persistable entity) ;
    void update(Persistable entity) ;
    void remove(Persistable entity);
    T find(ID key);
    List<T> getAll() ;

}