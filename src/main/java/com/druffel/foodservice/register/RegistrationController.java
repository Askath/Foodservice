/*
 * Project: foodservice Package: com.druffel.foodservice.controller File:
 * RegistrationController.java Created: Aug 18, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import java.util.Date;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.crypto.params.KeyParameter;

import com.druffel.foodservice.entity.Role;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.entity.UserActivationToken;
import com.druffel.foodservice.mail.Mailer;
import com.druffel.foodservice.security.encryption.PBKDF2Generator;
import com.druffel.foodservice.service.RoleService;
import com.druffel.foodservice.service.UserActivationTokenService;
import com.druffel.foodservice.service.UserService;

/**
 * <b>Description:</b><br>
 * Controller for the register view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class RegistrationController
{

    @Inject
    UserService userService;

    @Inject
    RoleService roleService;
    
    @Inject
    UserActivationTokenService userActivationTokenService;

    public boolean register(String username, String password, String passwordrep, boolean usesBank, String email)
    {
        User user = lookForUser(username);

        if (user == null)
        {
            if (password.equals(passwordrep))
            {
                PBKDF2Generator gen = new PBKDF2Generator(50000);
                gen.encryptKey(password.toCharArray());
                KeyParameter key = gen.getKey();
                User newUser = new User(getUserRole(), key.getKey(), gen.getSalt(), username.toLowerCase(), usesBank, email);
                persistUser(newUser);
                sendActivationMail(newUser, email);
                return true;
            }
        }
        else
        {
            addMessage(new FacesMessage("Benutzer existiert bereits mit diesem Kürzel"));
        }
        return false;
    }
    
    protected void addMessage(FacesMessage message)
    {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    protected void sendActivationMail(User newUser, String email)
    {
        UserActivationToken userActivationToken = new UserActivationToken();
        userActivationToken.setUser(newUser);
        userActivationToken.setToken(UUID.randomUUID().toString());
        userActivationToken.setExpirydate(new Date(new Date().getTime() + 600000));

        userActivationTokenService.create(userActivationToken);
        
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String prefix = (req.isSecure()) ? "https://" : "http://";
        
        String[] rec = new String[1];
        rec[0] = email;
        Mailer mailer = new Mailer("10.75.194.253", 25, "", "", "dialogssoftware@googlemail.com", "Foodservice");
        mailer.sendHtmlMail("Account bestätigung.", "Zum Aktivieren ihres gerade angelegten accounts bitte auf den folgenden Link klicken: \n" + prefix
                + req.getLocalAddr() + "/foodservice/activateUser.xhtml?token=" +userActivationToken.getToken(), rec, "Foodservice");
        FacesContext.getCurrentInstance().addMessage("mail", new FacesMessage("Eine Email mit dem Bestätigungslink wurde versand."));
        
    }

    public User lookForUser(String username)
    {
        try
        {
            return userService.findByInitials(username.toLowerCase());
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * @return Role for normal User
     */
    public Role getUserRole()
    {
        return roleService.findByID(2);
    }

    public void persistUser(User newUser)
    {
        userService.create(newUser);
    }

}
