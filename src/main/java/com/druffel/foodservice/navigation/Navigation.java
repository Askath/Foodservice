/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.navigation
 * File: 		Navigation.java
 *
 * Created:		Aug 17, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.navigation;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.druffel.foodservice.entity.Orderprocess;
import com.druffel.foodservice.service.OrderprocessService;

/**
 * <b>Description:</b><br>
 * Utility class that redirects the user to the current running phase
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class Navigation implements Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Inject
    OrderprocessService orderprocessService;

    public void redirectToPage(String page)
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try
        {
            ec.redirect(ec.getRequestContextPath() + "/" + page);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void redirectToCurrentPhase()
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(redirectionToCurrentPhase());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String redirectionToCurrentPhase()
    {
        Orderprocess op = orderprocessService.getLast();

        String redirection = "";

        if (op.getOrderstarttime() == null)
        {
            redirection = Pages.VOTE_PHASE;
        }
        else if (op.getOrderendtime() == null)
        {
            redirection = Pages.ORDER_PHASE;
        }
        else
        {
            if (op.getOrderedtime() == null)
            {
                redirection = Pages.WAITING_FOR_ORDER;
            }
            else if (op.getDeliveredtime() == null)
            {
                redirection = Pages.ORDERED;
            }
            else
            {
                redirection = Pages.DELIVERED;
            }

        }

        return redirection;

    }

}
