/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * PasswordResetBean.java Created: Aug 15, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.resetPassword;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.druffel.foodservice.entity.PasswordResetToken;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.navigation.Pages;
import com.druffel.foodservice.security.auth.PasswordValidator;

/**
 * <b>Description:</b><br>
 * Managed bean for the resetpassword view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@SessionScoped
public class PasswordResetManagedBean implements Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3807838772034565616L;

    @Inject
    PasswordResetController controller;

    private String password;

    private String passwordrep;

    private String token;
    
    @Inject
    Navigation nav;

    @PostConstruct
    public void init()
    {
        if (((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("token") == null)
        {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            nav.redirectToPage(Pages.HOME);
        }
    }

    public void overwritePassword()
    {
        PasswordResetToken resetToken = controller.getResetToken(token);

        if (resetToken != null && controller.checkIfTokenValid(resetToken))
        {
            if (password.equals(passwordrep))
            {
                if (new PasswordValidator().isPasswordValid(password))
                {
                    controller.overwritePassword(password, resetToken);
                    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                }
                else
                {
                    FacesContext.getCurrentInstance().addMessage("passwordrep", new FacesMessage(
                            "Passwort entspricht nicht den Richtlinien. Mind. 1 Kleinbuchstabe Mind. 1 Großbuchstabe Mind. 1 Sonderzeichen Mind. 8 Zeichen"));
                }
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage("passwordrep", new FacesMessage("Passwort stimmt nicht überein"));
            }
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage("tokeninv", new FacesMessage("Link ist nicht mehr gültig."));
            this.token = null;
        }
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPasswordrep()
    {
        return passwordrep;
    }

    public void setPasswordrep(String passwordrep)
    {
        this.passwordrep = passwordrep;
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
