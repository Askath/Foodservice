/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		OrderFacade.java
 *
 * Created:		Oct 6, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.druffel.foodservice.entity.Order;

/**
 * <b>Description:</b><br>
 *
 *  * Service class for Order Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class OrderService extends AbstractService<Order>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public OrderService() throws SecurityException
    {
        super(Order.class);
    }
    
    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }
}
