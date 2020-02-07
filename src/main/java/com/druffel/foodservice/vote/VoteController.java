/*
 * Project: foodservice Package: com.druffel.foodservice.controller File:
 * VoteController.java Created: Aug 18, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.vote;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.entity.Vote;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.phase.VotePhase;
import com.druffel.foodservice.security.auth.LoginStatus;
import com.druffel.foodservice.service.FoodserviceService;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.VoteService;
import com.druffel.foodservice.service.VotedservicesService;

/**
 * <b>Description:</b><br>
 * 
 * Controller for the vote view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class VoteController
{
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    VoteService voteService;

    @Inject
    FoodserviceService foodserviceService;

    @Inject
    VotedservicesService votedservicesService;

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    LoginStatus loginStatus;
    
    @Inject
    VotePhase votePhase;

    @Inject
    Navigation navigator;

    public void vote(User user, Foodservice foodservice) 
    {
        Vote v = new Vote(foodservice, orderprocessService.getLast(), user);
        voteService.create(v);
        votePhase.refresh();
        logger.info(user.getInitials() + " voted for " + foodservice.getName());
    }

    public void removeVote(User user)
    {
        for (Vote v : voteService.findAll())
        {
            if (v.getUser().getInitials().equals(user.getInitials()))
            {
                voteService.remove(v);
                votePhase.refresh();
                logger.info(user.getInitials() + " has withdrawn his vote from " + v.getFoodservice().getName());
            }
        }
    }

    public boolean currentUserHasVoted()
    {
        for (Vote vote : voteService.findAll())
        {
            if (vote.getUser().getInitials().equals(loginStatus.getCurrentUser().getInitials()))
            {
                return true;
            }
        }

        return false;
    }

    public List<Foodservice> resetToVoteservices()
    {
        return votePhase.servicesToVote();
    }

    public List<Vote> getVoteList()
    {
        return votePhase.getVoteList();
    }

    public List<Foodservice> getExcludedServices()
    {
        return votePhase.getExcludedServices();
    }
}
