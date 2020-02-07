/*
 * Project: foodservice Package: com.druffel.foodservice.controller File:
 * LoginController.java Created: Aug 17, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.login;

import java.util.Arrays;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.bouncycastle.crypto.params.KeyParameter;
import org.jboss.logging.Logger;

import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.security.auth.LoginStatus;
import com.druffel.foodservice.security.encryption.PBKDF2Generator;
import com.druffel.foodservice.service.UserService;

/**
 * <b>Description:</b><br>
 * Controller for Login view. Contains buisness logic needed for login process
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class LoginController
{

    private final Logger logger = Logger.getLogger(LoginController.class);
    
    @Inject
    UserService userService;

    @Inject
    LoginStatus loginStatus;

    public FacesMessage login(String username, String password)
    {
        
        User user = findUserInDatabase(username);

        if (user != null && user.getInitials().equals(username))
        {
            byte[] tempPass = user.getPassword();
            byte[] inputKey;
            inputKey = encryptPassword(user.getSalt(), password);

            if (Arrays.equals(inputKey, tempPass))
            {

                if (user.getActivated())
                {
                    setUserInformation(user);
                    logger.info(user.getInitials() + " has logged in.");
                    return null;
                }
                else
                {
                    logger.info(user.getInitials() + " account is not activated yet");
                    return new FacesMessage("Dieser Account ist noch nicht aktiviert. Bitte überprüfen sie ihre E-Mails für den Bestätigungslink.");
                }
            }
            else
            {
                logger.info("invalid credentials for " + username );
                return new FacesMessage("Benutzer oder Passwort falsch.");
            }
        }
        else
        {
            logger.info("invalid credentials for " + username );
            return new FacesMessage("Benutzer oder Passwort falsch.");
        }

    }
    
    protected void setUserInformation(User user)
    {
        loginStatus.setLoggedIn(true);
        loginStatus.setCurrentUser(user);
        if (user.getRole().getRoleid() == 1)
        {
            loginStatus.setAdmin(true);
        }
        else
        {
            loginStatus.setAdmin(false);
        }
    }

    protected byte[] encryptPassword(byte[] salt, String password)
    {
        PBKDF2Generator gen = new PBKDF2Generator(50000, salt);
        gen.encryptKey(password.toCharArray());
        KeyParameter key = gen.getKey();
        return key.getKey();
    }

    public User findUserInDatabase(String username)
    {
        try
        {
            return userService.findByInitials(username);
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

}
