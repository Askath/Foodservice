/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		PasswordResetController.java
 *
 * Created:		Aug 25, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.resetPassword;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.bouncycastle.crypto.params.KeyParameter;

import com.druffel.foodservice.entity.PasswordResetToken;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.navigation.Pages;
import com.druffel.foodservice.security.encryption.PBKDF2Generator;
import com.druffel.foodservice.service.PasswordResetTokenService;
import com.druffel.foodservice.service.UserService;

/**
 * <b>Description:</b><br>
 * 
 * Controller for PasswordReset view.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class PasswordResetController implements Serializable
{
    
    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Inject
    PasswordResetTokenService passwordResetTokenService; 

    @Inject
    UserService userService;
    
    @Inject
    Navigation nav;

    public void overwritePassword(String password, PasswordResetToken resetToken)
    {
            User user = resetToken.getUser();
            PBKDF2Generator gen = new PBKDF2Generator(50000);
            gen.encryptKey(password.toCharArray());
            KeyParameter key = gen.getKey();
            
            user.setPassword(key.getKey());
            user.setSalt(gen.getSalt());
            
            userService.merge(user);
            
            passwordResetTokenService.remove(resetToken);
            nav.redirectToPage(Pages.HOME);
    
    }

    public boolean checkIfTokenValid(PasswordResetToken resetToken)
    {
        if(new Date().after(resetToken.getExpirydate()))
        {
                passwordResetTokenService.remove(resetToken);
                return false;
        }
        return true;
        
    }
    
    public PasswordResetToken getResetToken(String token)
    {
        return passwordResetTokenService.findByToken(token);
    }
    
}
