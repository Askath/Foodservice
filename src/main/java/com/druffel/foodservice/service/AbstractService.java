/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		AbstractFacade.java
 *
 * Created:		Sep 26, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * <b>Description:</b><br>
 * Abstract class providing simple functions for database access
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
public abstract class AbstractService<T> 
{

    private final Class<T> entityClass;
    
    public AbstractService(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();

    protected CriteriaBuilder getCriteriaBuilder()
    {
        return getEntityManager().getCriteriaBuilder();
    }
    
    /**
     * @return      Returns List of all found Objects
     */
    public List<T> findAll()
    {
       CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
       cq.select(cq.from(entityClass));
       return getEntityManager().createQuery(cq).getResultList();
    }
    
    public void create(T entity)
    {
        getEntityManager().persist(entity);
    }
    
    public void remove(T entity)
    {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    /**
     * @return      Last entry of table
     */
    public T getLast()
    {
        TypedQuery<T> query = getEntityManager().createQuery("from " + entityClass.getSimpleName() 
            + " o order by o." + entityClass.getSimpleName().toLowerCase() + "id desc", entityClass);
        return query.getResultList().get(0);
    }
    
    public void merge(T entity)
    {
        getEntityManager().merge(entity);
    }
    
    public void deleteAll()
    {
        Query query = getEntityManager().createQuery("delete from " + entityClass.getSimpleName());
        query.executeUpdate();
    }
}
