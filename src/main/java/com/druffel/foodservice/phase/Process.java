/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.phase
 * File: 		Process.java
 *
 * Created:		Aug 18, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.phase;

import java.io.IOException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.druffel.foodservice.entity.Orderprocess;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.service.OrderprocessService;

/**
 * <b>Description:</b><br>
 * Bean that is managing the orderprocess at whole.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class Process
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private OrderPhase orderPhase;

    @Inject
    private VotePhase votePhase;

    @Inject
    OrderStatusPhase orderStatusPhase;

    @Inject
    private OrderprocessService orderprocessService;

    @Inject
    private Navigation navigator;
    /**
     * Aborts the current orderprocess and resets the process.
     * @throws IOException 
     */
    public void abortOrderprocess() throws IOException
    {
        Orderprocess op = orderprocessService.getLast();
        op.setDeliveredtime(new Date());
        op.setOrderendtime(new Date());
        op.setOrderedtime(new Date());
        op.setOrderstarttime(new Date());
        op.setAborted(true);
        orderprocessService.merge(op);
        votePhase.end();
        orderPhase.end();
        orderStatusPhase.refresh();
        orderStatusPhase.end();
        logger.info("orderprocess was aborted");
        navigator.redirectToPage(navigator.redirectionToCurrentPhase());
    }

    public Date getCurrentTime()
    {
        return new Date();
    }

}
