/*
 * Project: foodservice Package: com.druffel.foodservice.security.auth File:
 * LoginStatus.java Created: Aug 17, 2017 Author: AmonDruffel (Sophos Technology
 * GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.security.auth;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.navigation.Pages;

/**
 * <b>Description:</b><br>
 * 
 *Session bean that stores the user information of the logged in user
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Startup
@Named
@SessionScoped
public class LoginStatus implements Serializable
{
    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @Inject
    Navigation navigator;

    private User currentUser;

    private boolean loggedIn;

    private boolean admin;

    @PostConstruct
    private void init()
    {
        currentUser = null;
        loggedIn = false;
    }

    public String logout()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        currentUser = null;
        return "/index.xhtml?=faces-redirect=true";
    }

    public boolean redirectWhenLoggedIn()
    {
        if(isLoggedIn())
        {
            navigator.redirectToPage(navigator.redirectionToCurrentPhase());
            return true;
        }
        return false;
    }
    
    public boolean redirectWhenNotLoggedIn()
    {
        if(!isLoggedIn())
        {
            navigator.redirectToPage(Pages.HOME);
            return false;
        }
        return true;
    }
    
    public boolean isAdmin()
    {
        return admin;
    }

    public void setAdmin(boolean admin)
    {
        this.admin = admin;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }
}
