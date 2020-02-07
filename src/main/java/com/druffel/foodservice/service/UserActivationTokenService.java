/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.service
 * File: 		UserActivationTokenService.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.druffel.foodservice.entity.UserActivationToken;

@Stateless
public class UserActivationTokenService extends AbstractService<UserActivationToken>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;

    public UserActivationTokenService() throws SecurityException
    {
        super(UserActivationToken.class);
    }

    @Override
    public EntityManager getEntityManager()
    {
        return em;
    }
    
    public UserActivationToken findByToken(String token)
    {
        TypedQuery<UserActivationToken> q = em.createQuery("from UserActivationToken p where p.token = :token", UserActivationToken.class);
        q.setParameter("token", token);
        try
        {
            return q.getSingleResult();
        }
        catch(NoResultException nre)
        {
            return null;
        }
    }
    
}
