/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.bean
 * File: 		RegisterServiceManagedBean.java
 *
 * Created:		Nov 24, 2016
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <b>Description:</b><br>
 * 
 * Managedbean for the registerservice bean
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@ManagedBean
@RequestScoped
public class RegisterServiceManagedBean implements Serializable
{
    
    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -140555471683590137L;

    @Inject
    RegisterServiceController controller;
    
    private String link;
    
    @Size(min=4, message="Name zu kurz, mindestens 4 Zeichen")
    private String name;

    @Pattern(regexp = "^\\d*-?\\d*$", message = "Bitte Geben sie eine gültige Telefonnummer ein.")
    private String telephone;

    private boolean excluded;
    
    @Pattern(regexp = "^$|^\\d{1,2}(,\\d{1,2}|.\\d{1,2})?$", message = "Bitte geben sie einen Gültigen Dezimalwert an. Bsp 12,31.")
    private String minorderprice;
    
    public void registerService()
    {
       controller.registerService(name, link, telephone, excluded, minorderprice);
       clearFields();
    }
    
    private void clearFields()
    {
        telephone = "";
        name = "";
        link = "";
    }
    
    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public boolean isExcluded()
    {
        return excluded;
    }

    public void setExcluded(boolean excluded)
    {
        this.excluded = excluded;
    }

    public String getMinorderprice()
    {
        return minorderprice;
    }

    public void setMinorderprice(String minorderprice)
    {
        this.minorderprice = minorderprice;
    }
}
