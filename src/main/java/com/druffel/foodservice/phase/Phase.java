/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.phase
 * File: 		Phase.java
 *
 * Created:		Nov 8, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.phase;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import com.druffel.foodservice.navigation.Navigation;

public abstract class Phase
{
    
    @Inject
    Navigation navigator;
    
    private boolean started;
    
    private ScheduledExecutorService scheduler;

    private ScheduledFuture<?> future;
    
    @PreDestroy
    public abstract void preDestroy();
    
    public abstract void start();
    public abstract void end();
    public abstract boolean isRunning();
    public abstract void timeOver(boolean force);
    public abstract void refresh();
    
    
    public boolean isStarted()
    {
        return started;
    }
    public void setStarted(boolean started)
    {
        this.started = started;
    }
    public ScheduledExecutorService getScheduler()
    {
        return scheduler;
    }
    public void setScheduler(ScheduledExecutorService scheduler)
    {
        this.scheduler = scheduler;
    }
    public ScheduledFuture<?> getFuture()
    {
        return future;
    }
    public void setFuture(ScheduledFuture<?> future)
    {
        this.future = future;
    }
    
}
