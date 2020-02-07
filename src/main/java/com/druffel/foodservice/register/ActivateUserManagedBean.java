/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.view
 * File: 		ActivateUserManagedBean.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * <b>Description:</b><br>
 * 
 * Managed bean for the activateUser view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@SessionScoped
public class ActivateUserManagedBean implements Serializable
{
    
    @Inject
    ActivateUserController controller;

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String token;

    public boolean activate()
    {
        return controller.activateUser(token);
    }
    
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
    
}
