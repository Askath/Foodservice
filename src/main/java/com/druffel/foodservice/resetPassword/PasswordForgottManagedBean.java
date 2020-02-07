/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * PasswordForgottManagedBean.java Created: Aug 14, 2017 Author: AmonDruffel
 * (Sophos Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.resetPassword;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * <b>Description:</b><br>
 * 
 *Managed bean for the forgottpassword view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class PasswordForgottManagedBean implements Serializable
{

    /**
     * <code>serialVersionUID</code>
     */

    private static final long serialVersionUID = 1L;

    private String initials;

    @Inject
    private PasswordForgottenController controller;

    public void resetPassword()
    {
        controller.resetPassword(initials);
    }

    public String getInitials()
    {
        return initials;
    }

    public void setInitials(String initials)
    {
        this.initials = initials;
    }


}
