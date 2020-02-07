/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		ActivateUserController.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.entity.UserActivationToken;
import com.druffel.foodservice.service.UserActivationTokenService;
import com.druffel.foodservice.service.UserService;

/**
 * <b>Description:</b><br>
 * 
 * Controller for the Activate User view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class ActivateUserController implements Serializable 
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private final Logger logger = Logger.getLogger(ActivateUserController.class);

    @Inject
    UserActivationTokenService userActivationTokenService;
    
    @Inject
    UserService userService;
    
    public boolean activateUser(String token)
    {
        if(token == null || token.isEmpty() || token.equals(""))
        {
            logger.info("Invalid User Activation Token.");
            return false;
        }
        
        UserActivationToken activationToken = findTokenByTokenString(token);
        if(activationToken != null && activationToken.getExpirydate().after(new Date()))
        {
            User user = activationToken.getUser();
            user.setActivated(true);
            mergeUser(user);
            removeToken(activationToken);
            logger.info("User " + user.getInitials() + " was activated successfully");
            return true;
        }
        else
        {
            logger.error("User activation token is invalid or expired.");
            return false;
        }
    }
    
    protected void mergeUser(User user)
    {
        userService.merge(user);
    }
    
    protected void removeToken(UserActivationToken token)
    {
        userActivationTokenService.remove(token);
    }
    
    protected UserActivationToken findTokenByTokenString(String token)
    {
        return userActivationTokenService.findByToken(token);
    }
    
}
