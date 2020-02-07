/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		OrderprocessFacade.java
 *
 * Created:		Sep 27, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */

package com.druffel.foodservice.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Orderprocess;
import com.druffel.foodservice.entity.User;


/**
 * <b>Description:</b><br>
 * 
 * * Service class for Orderprocess Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class OrderprocessService extends AbstractService<Orderprocess>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public OrderprocessService() throws SecurityException
    {
        super(Orderprocess.class);
    }

    public void startNewProcess(User currentUser)
    {
        Orderprocess newProcess = new Orderprocess(currentUser, new Date());
        create(newProcess);
    }
    
    public void endOrderPhase()
    {
        Orderprocess op = getLast();
        op.setOrderendtime(new Date());
        merge(op);
    }
    
    public void setDeliveryTime(Date deliverytime)
    {
        Orderprocess op = getLast();
        op.setDeliveryplannedtime(deliverytime);
        merge(op);
    }
    
    public void startOrderPhase(Foodservice finalVotedFoodservice)
    {
        Orderprocess op = getLast();
        op.setFoodservice(finalVotedFoodservice);
        op.setOrderstarttime(new Date());
        merge(op);  
    }
    
    public void appointNewDeliveryTime()
    {
        Orderprocess op = getLast();
        op.setDeliveryplannedtime(null);
        op.setOrderedtime(null);
        merge(op);
    }
    
    public void orderEnd()
    {
        Orderprocess op = getLast();
        op.setDeliveredtime(new Date());
        op.setOrderedtime(new Date());
        merge(op);  
    }
    
    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }

}
