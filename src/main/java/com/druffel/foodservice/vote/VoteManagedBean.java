/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * VoteManagedBean.java Created: Aug 16, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.vote;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.entity.Vote;
import com.druffel.foodservice.navigation.Navigation;

/**
 * <b>Description:</b><br>
 * 
 * Managed bean for the vote view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class VoteManagedBean implements Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Inject
    VoteController controller;
    
    @Inject
    Navigation navigator;
    
    private List<Vote> voteList;

    private List<Foodservice> servicesToVote;

    private List<Foodservice> excludedServices;

    @PostConstruct
    public void init()
    {
        servicesToVote = controller.resetToVoteservices();
        voteList = controller.getVoteList();
        excludedServices = controller.getExcludedServices();
    }
    
    public boolean currentUserHasVoted()
    {
       return controller.currentUserHasVoted();
    }

    public void vote(User user, Foodservice foodservice)
    {
        controller.vote(user, foodservice);
        navigator.redirectToPage(navigator.redirectionToCurrentPhase());
    }


    public void removeVote(User user)
    {
        controller.removeVote(user);
        navigator.redirectToPage(navigator.redirectionToCurrentPhase());
    }
    
    public List<Vote> getVoteList()
    {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList)
    {
        this.voteList = voteList;
    }

    public List<Foodservice> getServicesToVote()
    {
        servicesToVote = controller.resetToVoteservices(); 
        return servicesToVote;
    }

    public void setServicesToVote(List<Foodservice> servicesToVote)
    {
        this.servicesToVote = servicesToVote;
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
