/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		VoteFacade.java
 *
 * Created:		Sep 29, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.druffel.foodservice.entity.Vote;

/**
 * <b>Description:</b><br>
 * 
 *  * Service class for Vote Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class VoteService extends AbstractService<Vote>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public VoteService() throws SecurityException
    {
        super(Vote.class);
    }

    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }
    
}
