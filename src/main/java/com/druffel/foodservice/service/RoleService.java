/*
 * Project:     foodservice
 * Package:     com.druffel.foodservice.facade
 * File:        UserFacade.java
 *
 * Created:     Sep 26, 2016
 * Author:      AmonDruffel (Sophos Technology GmbH)
 * Copyright:   (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.druffel.foodservice.entity.Role;

/**
 * <b>Description:</b><br>
 * 
 *  * Service class for Role Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class RoleService extends AbstractService<Role>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public RoleService() throws SecurityException
    {
        super(Role.class);
    }
    
    public Role findByID(int id)
    {
        
        TypedQuery<Role> q = em.createQuery("from Role r where r.roleid = :id", Role.class);
        q.setParameter("id", id);
        try
        {
            return q.getSingleResult();
        }
        catch(NoResultException nre)
        {
            return null;
        }
    }

    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }
}
