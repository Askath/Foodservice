/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * LoginManagedBean.java Created: Sep 26, 2016 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.login;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.druffel.foodservice.navigation.Navigation;

/**
 * <b>Description:</b><br>
 * ManagedBean that handles the login process as well as stores the logged in
 * User
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class LoginManagedBean implements Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8482051562327706876L;

    @Inject
    LoginController controller;
    
    @Inject
    Navigation navigator;
    
    private String username;

    private String password;

    public String login()
    {
        
        FacesMessage message = controller.login(username.toLowerCase(), password);
        if(message != null)
        {
            FacesContext.getCurrentInstance().addMessage("asd", message);
            return "";
        }
        else
        {
            return navigator.redirectionToCurrentPhase() + "?faces-redirect=true";
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
