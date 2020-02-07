/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.service
 * File: 		ConfigurationService.java
 *
 * Created:		Aug 10, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.druffel.foodservice.entity.Configuration;

/**
 * <b>Description:</b><br>
 * 
 * Service class for Configuration Entity
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Stateless
public class ConfigurationService extends AbstractService<Configuration>
{

    @PersistenceContext(unitName = "foodservice")
    private EntityManager em;
    
    public ConfigurationService() throws SecurityException
    {
        super(Configuration.class);
    }
    
    public Configuration getConfigurationByName(String param)
    {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Configuration> cq = cb.createQuery(Configuration.class);
        Root<Configuration> r = cq.from(Configuration.class);
        cq.select(r);
        cq.where(cb.equal(r.get("confkey"), param));
        return getEntityManager().createQuery(cq).getSingleResult();
    }
    
    @Override 
    public EntityManager getEntityManager()
    {
        return em;
    }
}