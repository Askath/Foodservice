/*
 * Project: foodservice Package: com.druffel.foodservice.phase File:
 * VotePhase.java Created: Aug 18, 2017 Author: AmonDruffel (Sophos Technology
 * GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.phase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.druffel.foodservice.configuration.ConfigurationKeys;
import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Vote;
import com.druffel.foodservice.entity.Votedservices;
import com.druffel.foodservice.service.ConfigurationService;
import com.druffel.foodservice.service.FoodserviceService;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.VoteService;
import com.druffel.foodservice.service.VotedservicesService;

/**
 * <b>Description:</b><br>
 * Application scoped Bean that is managing the vote phase.
 * is responsible for the timer of this phase.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Startup
@Named
@ApplicationScoped
public class VotePhase extends Phase
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    ConfigurationService configurationService;

    @Inject
    VoteService voteService;

    @Inject
    FoodserviceService foodserviceService;

    @Inject
    VotedservicesService votedservicesService;

    @Inject
    OrderPhase orderPhase;

    private Date timerStarted;

    private Date timerEnd;

    private int timeLeft;

    private List<Vote> voteList;

    private int interval;

    private List<Foodservice> foodserviceList;

    private List<Votedservices> votedservicesList;
    
    private List<Foodservice> excludedServices;

    @PostConstruct
    public void init()
    {
        excludedServices = new ArrayList<>();
        foodserviceList = new ArrayList<>();
        votedservicesList = new ArrayList<>();
        voteList = new ArrayList<>();
        setInterval(Integer.parseInt(configurationService.getConfigurationByName(ConfigurationKeys.PHASE_TIME).getConfvalue()));
        setStarted(false);
        setScheduler(Executors.newSingleThreadScheduledExecutor());
        logger.info("initialized VotePhase");
    }

    /**
     * Start Vote process
     */
    @Override
    public void start()
    {
        foodserviceList = foodserviceService.findAll();
        votedservicesList = votedservicesService.findAll();
        excludedServices = foodserviceService.getExcludedServices();
        voteList = voteService.findAll();
        setStarted(true);
        timerStarted = orderprocessService.getLast().getStarttime();
        timerEnd = new Date(
                timerStarted.getTime() + Long.parseLong(configurationService.getConfigurationByName(ConfigurationKeys.PHASE_TIME).getConfvalue()) * 60 * 1000);
        setTimeLeft(((int) timerEnd.getTime() - (int) new Date().getTime()) / 1000);
        setFuture(getScheduler().scheduleAtFixedRate(() -> {
            voteList = voteService.findAll();
            foodserviceList = foodserviceService.findAll();
            votedservicesList = votedservicesService.findAll();
            excludedServices = foodserviceService.getExcludedServices();
        }, 5, 10, TimeUnit.SECONDS));
        logger.info("refreshed");
        logger.info("started vote Phase");
    }

    @Override
    @PreDestroy
    public void preDestroy()
    {
        getScheduler().shutdownNow();
    }

    @Override
    public void end()
    {
        if (getFuture() != null)
            getFuture().cancel(false);
        setStarted(false);
    }

    public boolean isVotePhase()
    {
        if (orderprocessService.getLast().getOrderstarttime() == null)
        {
            return true;
        }
        return false;
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
     * Checks if votePhase is over.
     * 
     * @param force End Phase, even if Phase is not over
     */
    @Override
    public void timeOver(boolean force)
    {
        logger.info("checked if phase over");
        if (isStarted())
        {
            if (new Date().after(new Date(timerEnd.getTime())) || force)
            {
                timeLeft = 0;
                endPhase();
                setStarted(false);
                logger.info("vote phase ended");
            }
            else
            {
                timeLeft = ((int) timerEnd.getTime() - (int) new Date().getTime()) / 1000;
            }
        }
        else
        {
            navigator.redirectToPage(navigator.redirectionToCurrentPhase());
        }
    }

    @Override
    public void refresh()
    {
        voteList = voteService.findAll();
        foodserviceList = foodserviceService.findAll();
        votedservicesList = votedservicesService.findAll();
        excludedServices = foodserviceService.getExcludedServices();
        logger.info("refreshed");
    }

    /**
     * ends Phase and saves result to Database
     */
    public void endPhase()
    {
        List<Vote> votesForNormalServices = new ArrayList<>();

        for (Vote v : voteList)
        {
            if (!v.getFoodservice().isExcluded() && !v.getFoodservice().isHidden())
            {
                votesForNormalServices.add(v);
            }
        }

        List<Foodservice> mostVoted = foodserviceService.getMostVoted();

        if (mostVoted.size() == 1)
        {
            finishVote(mostVoted.get(0));
            logger.info("winner of vote is: " + mostVoted.get(0).getName());
        }
        else if (mostVoted.size() > 1)
        {
            int index = new Random().nextInt(mostVoted.size());
            finishVote(mostVoted.get(index));
            logger.info("winner of vote is: " + mostVoted.get(index).getName() + " from " + mostVoted.size() + " winners");
        }
    }

    /**
     * Resets vote
     * 
     * @param service
     */
    public void finishVote(Foodservice service)
    {
        persistOrderprocessStartTime();
        removeLostServices(service);
        navigator.redirectToPage(navigator.redirectionToCurrentPhase());
        orderPhase.start();
    }

    /**
     * Removes services that lost vote
     * 
     * @param service
     */
    public void removeLostServices(Foodservice service)
    {

        for (Votedservices v : votedservicesService.findAll())
        {
            if (!v.getFoodservice().getName().equals(service.getName()))
            {
                logger.info("deleted " + v.getFoodservice().getName());
                votedservicesService.remove(v);
            }
        }
    }

    public List<Foodservice> servicesToVote()
    {
        List<Foodservice> temp = new ArrayList<>();

        for (Foodservice f : foodserviceList)
        {
            for (Votedservices v : votedservicesList)
            {
                if (f.getName().equals(v.getFoodservice().getName()))
                {
                    temp.add(f);
                }
            }
        }

        return temp;
    }

    protected void persistOrderprocessStartTime()
    {
        orderprocessService.startOrderPhase(votedservicesService.getLast().getFoodservice());
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

    public List<Vote> getVoteList()
    {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList)
    {
        this.voteList = voteList;
    }

    public int getInterval()
    {
        return interval;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public List<Votedservices> getVotedservicesList()
    {
        return votedservicesList;
    }

    public void setVotedservicesList(List<Votedservices> votedservicesList)
    {
        this.votedservicesList = votedservicesList;
    }

    public List<Foodservice> getFoodserviceList()
    {
        return foodserviceList;
    }

    public void setFoodserviceList(List<Foodservice> foodserviceList)
    {
        this.foodserviceList = foodserviceList;
    }

    public void addToVotes(Vote v)
    {
        voteList.add(v);
    }

    public List<Foodservice> getExcludedServices()
    {
        return excludedServices;
    }

    public void setExcludedServices(List<Foodservice> excludedServices)
    {
        this.excludedServices = excludedServices;
    }

}
