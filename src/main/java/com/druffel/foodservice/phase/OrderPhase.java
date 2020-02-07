/*
 * Project: foodservice Package: com.druffel.foodservice.phase File:
 * OrderPhase.java Created: Aug 18, 2017 Author: AmonDruffel (Sophos Technology
 * GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.phase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.druffel.foodservice.configuration.ConfigurationKeys;
import com.druffel.foodservice.entity.Configuration;
import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Order;
import com.druffel.foodservice.service.ConfigurationService;
import com.druffel.foodservice.service.OrderService;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.VotedservicesService;

/**
 * <b>Description:</b><br>
 * Applikation scoped class that stores informations about the running order
 * phase. Responsible for starting and managing the timer.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Startup
@Named
@ApplicationScoped
public class OrderPhase extends Phase
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    ConfigurationService configurationService;

    @Inject
    OrderService orderService;

    @Inject
    VotedservicesService votedservicesService;

    @Inject
    OrderStatusPhase orderStatusPhase;

    private Date timerStarted;

    private Date timerEnd;

    private int timeLeft;

    private int interval;

    private List<Order> orderList;

    private Foodservice votedFoodservice;

    private ScheduledExecutorService scheduler;

    private ScheduledFuture<?> future;
    
    private boolean showRecieptDialog = false;

    @PostConstruct
    public void init()
    {
        orderList = new ArrayList<>();
        setStarted(false);
        setInterval(Integer.parseInt(configurationService.getConfigurationByName(ConfigurationKeys.PHASE_TIME).getConfvalue()));
        scheduler = Executors.newSingleThreadScheduledExecutor();
        logger.info("initialized OrderPhase");
    }

    @Override
    public void refresh()
    {
        orderList = orderService.findAll();
    }

    /**
     * Starts order
     */
    @Override
    public void start()
    {
        Configuration reciept = configurationService.getConfigurationByName("reciept");
        if(reciept.getConfvalue().trim().length() < 1)
        {
            showRecieptDialog = true;
        }
        
        if(!showRecieptDialog)
        {
          kickOff();
        }
        
    }

    public void kickOff()
    {
        votedFoodservice = votedservicesService.getLast().getFoodservice();
        logger.info(votedFoodservice);
        setStarted(true);
        future = scheduler.scheduleAtFixedRate(() -> {
            orderList = orderService.findAll();
        }, 5, 5, TimeUnit.SECONDS);
        orderList = orderService.findAll();
        timerStarted = orderprocessService.getLast().getOrderstarttime();
        setTimerEnd(new Date(
                timerStarted.getTime() + Long.parseLong(configurationService.getConfigurationByName(ConfigurationKeys.PHASE_TIME).getConfvalue()) * 60 * 1000));
        setTimeLeft(((int) timerEnd.getTime() - (int) new Date().getTime()) / 1000);
        logger.info("order phase was started");
    }
    
    @Override
    public boolean isRunning()
    {
        if (!isStarted())
        {
            navigator.redirectToPage(navigator.redirectionToCurrentPhase());
        }

        return isStarted();
    }

    /**
     * checks if phase is over
     * 
     * @param force
     */
    @Override
    public void timeOver(boolean force)
    {
        if (isStarted())
        {
            if (new Date().after(new Date(timerEnd.getTime())) || force)
            {
                setTimeLeft(0);
                orderprocessService.endOrderPhase();
                setStarted(false);
                logger.info("order phase ended");
                orderStatusPhase.start();
                navigator.redirectToPage(navigator.redirectionToCurrentPhase());
            }
            else
            {
                setTimeLeft(((int) timerEnd.getTime() - (int) new Date().getTime()) / 1000);
            }
        }
        else
        {
            navigator.redirectToPage(navigator.redirectionToCurrentPhase());
        }

    }

    @Override
    public void end()
    {
        setStarted(false);
        if (future != null)
            future.cancel(false);
    }

    public boolean isOrderPhase()
    {
        if (orderprocessService.getLast().getOrderendtime() == null && orderprocessService.getLast().getOrderstarttime() != null)
        {
            return true;
        }
        return false;
    }

    public int getTimeLeft()
    {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft)
    {
        this.timeLeft = timeLeft;
    }

    public Date getTimerStarted()
    {
        return timerStarted;
    }

    public void setTimerStarted(Date timerStarted)
    {
        this.timerStarted = timerStarted;
    }

    public Date getTimerEnd()
    {
        return timerEnd;
    }

    public void setTimerEnd(Date timerEnd)
    {
        this.timerEnd = timerEnd;
    }

    public List<Order> getOrderList()
    {
        return orderList;
    }

    public void setOrderList(List<Order> orderList)
    {
        this.orderList = orderList;
    }

    public int getInterval()
    {
        return interval;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public Foodservice getVotedFoodservice()
    {
        return votedFoodservice;
    }

    public void setVotedFoodservice(Foodservice votedFoodservice)
    {
        this.votedFoodservice = votedFoodservice;
    }

    @Override
    @PreDestroy
    public void preDestroy()
    {
        getScheduler().shutdownNow();
    }

    public boolean isShowRecieptDialog()
    {
        return showRecieptDialog;
    }

    public void setShowRecieptDialog(boolean showRecieptDialog)
    {
        this.showRecieptDialog = showRecieptDialog;
    }

}
