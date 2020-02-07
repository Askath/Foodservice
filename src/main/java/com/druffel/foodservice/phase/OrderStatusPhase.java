/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.phase
 * File: 		OrderStatusPhase.java
 *
 * Created:		Aug 21, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.phase;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Orderprocess;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.VotedservicesService;

/**
 * <b>Description:</b><br>
 * Checks if the order process is currently in the orderstatus phase.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@ApplicationScoped
@Startup
public class OrderStatusPhase extends Phase
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    VotedservicesService votedservicesService;

    private Foodservice votedService;

    private Date deliveryTime;

    private Orderprocess orderProcess;

    private ScheduledExecutorService scheduler;

    private ScheduledFuture<?> future;

    @PostConstruct
    public void init()
    {
        setStarted(false);
        scheduler = Executors.newSingleThreadScheduledExecutor();
        setVotedService(votedservicesService.getLast().getFoodservice());
        setOrderProcess(orderprocessService.getLast());
        setDeliveryTime(orderProcess.getDeliveryplannedtime());
    }

    @Override
    public void refresh()
    {
        orderProcess = orderprocessService.getLast();
        setVotedService(votedservicesService.getLast().getFoodservice());       
        setDeliveryTime(orderProcess.getDeliveryplannedtime());
    }
    
    @Override
    public void start()
    {
        setStarted(true);
        refresh();
        orderProcess = orderprocessService.getLast();
        future = scheduler.scheduleAtFixedRate(() -> {
            orderProcess = orderprocessService.getLast();
            if (orderProcess.getDeliveredtime() != null)
            {
                end();
            }
        }, 5, 5, TimeUnit.SECONDS);
        logger.info("OrderStatusPhase has started");
    }

    @Override
    public void end()
    {
        if (future != null)
            future.cancel(false);
        setStarted(false);
    }

    public void isOrderStatusPhase()
    {
        Orderprocess op = orderprocessService.getLast();

        if (op.getOrderstarttime() != null || op.getOrderendtime() != null)
        {
            navigator.redirectToPage(navigator.redirectionToCurrentPhase());
        }
    }

    public Orderprocess getOrderProcess()
    {
        return orderProcess;
    }

    public void setOrderProcess(Orderprocess orderProcess)
    {
        this.orderProcess = orderProcess;
    }

    public Date getDeliveryTime()
    {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }

    public Foodservice getVotedService()
    {
        return votedService;
    }

    public void setVotedService(Foodservice votedService)
    {
        this.votedService = votedService;
    }

    @Override
    public void preDestroy()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isRunning()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void timeOver(boolean force)
    {
        // TODO Auto-generated method stub
        
    }

   
}
