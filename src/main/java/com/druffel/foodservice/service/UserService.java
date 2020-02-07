/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		UserFacade.java
 *
 * Created:		Sep 26, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.druffel.foodservice.entity.Order;
import com.druffel.foodservice.entity.User;

/**
 * <b>Description:</b><br>
 * 
 *  * Service class for User Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class UserService extends AbstractService<User>
{

    @Inject
    OrderService orderService;
    
    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public UserService() throws SecurityException
    {
        super(User.class);
    }

    public List<Order> getOrderListOfUser(User currentUser)
    {
        List<Order> orderList = new ArrayList<>();
        
        for(Order o : orderService.findAll())
        {
            if(o.getUser().getInitials().equals(currentUser.getInitials()))
            {
                orderList.add(o);
            }
        }
        return orderList;
    }
    
    public double getPriceSumForUser(User user)
    {
        double sum = 0D;
        for (Order o : orderService.findAll())
        {
            if (o.getUser().getInitials().equals(user.getInitials()))
            {
                sum = sum + o.getPrice();
            }
        }
        return sum;
    }
    
    public HashMap<String, Double> getPriceMapOfUser()
    {
        HashMap<String, Double> priceMap = new HashMap<>();
        
        for(User u : findAll())
        {
            double s = 0;
            for (Order o : orderService.findAll())
            {
                if (u.getInitials().equals(o.getUser().getInitials()))
                {
                    s = s + o.getPrice();
                }
            }
            priceMap.put(u.getInitials(), s);
        } 
        
        return priceMap;
    }
    
    public User findByInitials(String initials)
    {
        TypedQuery<User> q = em.createQuery("from User u where u.initials = :initials", User.class);
        q.setParameter("initials", initials);
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
