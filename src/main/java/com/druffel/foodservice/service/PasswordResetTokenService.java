/*
 * Project: foodservice Package: com.druffel.foodservice.service File:
 * PasswordResetTokenService.java Created: Aug 14, 2017 Author: AmonDruffel
 * (Sophos Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.druffel.foodservice.entity.PasswordResetToken;

/**
 * <b>Description:</b><br>
 * 
 *  * Service class for Passwordresettoken Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class PasswordResetTokenService extends AbstractService<PasswordResetToken>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;

    public PasswordResetTokenService() throws SecurityException
    {
        super(PasswordResetToken.class);
    }

    @Override
    public EntityManager getEntityManager()
    {
        return em;
    }
    
    public PasswordResetToken findByToken(String token)
    {
        TypedQuery<PasswordResetToken> q = em.createQuery("from PasswordResetToken p where p.token = :token", PasswordResetToken.class);
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