/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		FoodserviceFacade.java
 *
 * Created:		Sep 29, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Votedservices;

/**
 * <b>Description:</b><br>
 * 
 * * Service class for Foodservice Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class FoodserviceService extends AbstractService<Foodservice>
{

    @Inject
    VotedservicesService votedservicesService;
    
    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public FoodserviceService() throws SecurityException
    {
        super(Foodservice.class);
    }

    public Foodservice getServiceByName(String name)
    {
        TypedQuery<Foodservice> query = getEntityManager().
                createNamedQuery("Foodservice.findByName", Foodservice.class);
        
        Foodservice service = null;
        try
        {
            service = query.setParameter("name", name).getSingleResult();
        }catch(NoResultException e)
        {
            e.printStackTrace();
        }
        return service;
    }
    
    public List<Foodservice> nonHiddenService()
    {
        List<Foodservice> foodserviceList = findAll();
        List<Foodservice> t = new ArrayList<>();
        for(Foodservice s : foodserviceList)
        {
            if(s.isHidden())
            {
                t.add(s);
            }
        }
        foodserviceList.removeAll(t);
        return foodserviceList;
    }
    
    public List<Foodservice> nonHiddenNotExludedService()
    {
        List<Foodservice> foodserviceList = findAll();
        List<Foodservice> t = new ArrayList<>();
        for(Foodservice s : foodserviceList)
        {
            if(s.isHidden() ||
                   s.isExcluded())
            {
                t.add(s);
            }
        }
        foodserviceList.removeAll(t);
        return foodserviceList;
    }
    
    public List<Foodservice> getExcludedServices()
    {
        List<Foodservice> temp = findAll();
        List<Foodservice> returnList = new ArrayList<>();
        
        for(Foodservice f : temp)
        {
            if(f.isExcluded())
            {
                returnList.add(f);
            }
        }
        
        return returnList;
    }
    
    public List<Foodservice> getMostVoted()
    {
        List<Foodservice> mostVoted = new ArrayList<>();
        List<Votedservices> votedList = votedservicesService.findAll();
        
        for(Foodservice f : findAll())
        {
            for(Votedservices v : votedList)
            {
                if(v.getFoodservice().getName().equals(f.getName()) && !f.isExcluded())
                {
                    mostVoted.add(f);
                }
            }
        }
        
        int i = 0;
        for(Foodservice f : mostVoted)
        {
            if(f.getVotes().size() > i)
            {
                i = f.getVotes().size();
            }
        }
        List<Foodservice> toremove = new ArrayList<>();
        for(Foodservice f : mostVoted)
        {
            if(f.getVotes().size() != i)
            {
                toremove.add(f);
            }
        }
        mostVoted.removeAll(toremove);
        return mostVoted;
    }
    
    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }
}
