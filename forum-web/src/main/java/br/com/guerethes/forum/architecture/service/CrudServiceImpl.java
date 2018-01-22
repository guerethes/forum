package br.com.guerethes.forum.architecture.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public abstract class CrudServiceImpl<E, K extends Serializable> implements CrudService<E, K> {

    @PersistenceContext
    EntityManager entityManager;

    private Class<E> clazz;

    protected Class<? extends E> daoType;

    public CrudServiceImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
        clazz = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public void add(Persistable entity) {
        entityManager.persist( entity );
        entityManager.flush();
    }

    @Override
    public void saveOrUpdate(Persistable entity) {
        if ( entity.getId() == null )
            add(entity);
        else
            update(entity);
    }

    @Override
    public void update(Persistable entity) {
        entityManager.merge( entity );
    }

    @Override
    public void remove(Persistable entity) {
        entityManager.remove(entity);
    }

    @Override
    public E find(K key) {
        E result = entityManager.find(clazz, key);
        entityManager.refresh(result);
        return result;
    }

    @Override
    public List<E> getAll() {
        return entityManager.createQuery( "from " + clazz.getName() ).getResultList();
    }

}