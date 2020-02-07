/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * RegistrationManagedBean.java Created: Sep 27, 2016 Author: AmonDruffel
 * (Sophos Technology GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

import com.druffel.foodservice.security.auth.PasswordValidator;

/**
 * <b>Description:</b><br>
 * Managed bean for the register view
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
@ManagedBean
@RequestScoped
public class RegistrationManagedBean implements Serializable
{
    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Inject
    RegistrationController controller;

    @Pattern(regexp = "^[A-Za-z]{2,4}$", message = "Kürzel darf nur aus max 2-4 Buchstaben bestehen.")
    private String username;

    private String password;

    private String passwordrep;
    
    private boolean usesBank;
    
    @Pattern( regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Bitte gültige Email Adresse eingeben.")
    private String email;

    public boolean register()
    {
        PasswordValidator validator = new PasswordValidator();
        if(!validator.isPasswordValid(password))
        {
            FacesContext.getCurrentInstance().addMessage("asd", new FacesMessage("Passwort entspricht nicht den Richtlinien. Mind. 1 Kleinbuchstabe Mind. 1 Großbuchstabe Mind. 1 Sonderzeichen Mind. 8 Zeichen"));
            return false;
        }
        return controller.register(username, password, passwordrep, usesBank, email);
    }

    public String getServerAdress()
    {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String prefix = (req.isSecure()) ? "https://":"http://";
        return new String(prefix + req.getLocalAddr() + ":" + req.getLocalPort());
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

    public String getPasswordrep()
    {
        return passwordrep;
    }

    public void setPasswordrep(String passwordrep)
    {
        this.passwordrep = passwordrep;
    }

    public boolean isUsesBank()
    {
        return usesBank;
    }

    public void setUsesBank(boolean usesBank)
    {
        this.usesBank = usesBank;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
