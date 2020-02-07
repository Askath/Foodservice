/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		PasswordForgottenController.java
 *
 * Created:		Aug 25, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.resetPassword;

import java.util.Date;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.druffel.foodservice.entity.PasswordResetToken;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.mail.Mailer;
import com.druffel.foodservice.service.PasswordResetTokenService;
import com.druffel.foodservice.service.UserService;

/**
 * <b>Description:</b><br>
 * 
 *Controller for the PasswordForgotten view.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class PasswordForgottenController
{

    @Inject
    private UserService userService;

    @Inject
    private PasswordResetTokenService passwordResetTokenService;

    public void resetPassword(String initials)
    {
        User user = findUserByInitials(initials);

        if(user != null)
        {
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(UUID.randomUUID().toString());
            resetToken.setUser(user);
            resetToken.setExpirydate(new Date(new Date().getTime() + 600000));
            
            createPasswordResetToken(resetToken);
            
            String prefix = getPrefix();
            String localAddr = getLocalAddr();
            
            sendMail(user, prefix, localAddr, resetToken);
            
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage("email", new FacesMessage("Benutzer mit diesen Daten nicht gefunden."));
        }
    }
    
    protected void sendMail(User user, String prefix, String localAddr, PasswordResetToken resetToken)
    {
        String[] rec = new String[1];
        rec[0] = user.getEmail();
        Mailer mailer = new Mailer("10.75.194.253", 25, "", "", "dialogssoftware@googlemail.com", "Foodservice");
        mailer.sendHtmlMail("Foodservice - Password reset", "Zum zurücksetzten des Passworts bitte auf den folgenden Link klicken: \n" + prefix
                + localAddr + "/foodservice/resetPassword/resetPassword.xhtml?token=" + resetToken.getToken(), rec, "Foodservice");
        FacesContext.getCurrentInstance().addMessage("mail", new FacesMessage("Eine Mail an den Accountinhaber wurde versand!"));
    }
    
    protected String getLocalAddr()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getLocalAddr();
    }
    
    protected String getPrefix()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (req.isSecure()) ? "https://" : "http://";
    }
    
    protected User findUserByInitials(String initials)
    {
        return userService.findByInitials(initials);
    }
    
    protected void createPasswordResetToken(PasswordResetToken passwordResetToken)
    {
        passwordResetTokenService.create(passwordResetToken);
    }
    
}
