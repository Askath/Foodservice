/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.bean
 * File: 		FoodplansManagedBean.java
 *
 * Created:		Mar 16, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.foodplans;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * <b>Description:</b><br>
 * 
 *Managedbean to provide information for the foodplans.xhtml
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class FoodplanManagedBean implements Serializable
{
 
    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String image;
    
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    @PostConstruct
    public void init()
    {
        image = "busche.pdf";
    }
    
    public String getServerAdress()
    {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String prefix = (req.isSecure()) ? "https://":"http://";
        String serverAdress = new String(prefix + req.getLocalAddr() + ":" + req.getLocalPort());
        logger.info("Builded ServerAdress: " +  serverAdress);
        return serverAdress;
    }
    
    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public boolean isShowDialog()
    {
        return (image.equals("busche.pdf")) ? true:false;
    }
}
