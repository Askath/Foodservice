/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.facade
 * File: 		VotedservicesFacade.java
 *
 * Created:		Oct 6, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Votedservices;

/**
 * <b>Description:</b><br>
 * 
 * * Service class for Votedservices Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class VotedservicesService extends AbstractService<Votedservices>
{

    @Inject
    FoodserviceService foodserviceService;
    
    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public VotedservicesService() throws SecurityException
    {
        super(Votedservices.class);
    }

    public void setNewVotedServices(List<String> selectedServices)
    {
        List<Foodservice> fs = foodserviceService.findAll();
        for (Foodservice fsl : fs)
        {
            for (int i = 0; i < selectedServices.size(); i++)
            {
                if (selectedServices.get(i).equals(fsl.getName()) && !fsl.isHidden())
                {
                    create(new Votedservices(fsl));
                }
            }
        }
    }
    
    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }
}
