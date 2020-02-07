/*
 * Project: foodservice Package: com.druffel.foodservice.controller File:
 * StartOrderController.java Created: Aug 25, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.order;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.druffel.foodservice.entity.Configuration;
import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.phase.VotePhase;
import com.druffel.foodservice.service.ConfigurationService;
import com.druffel.foodservice.service.FoodserviceService;
import com.druffel.foodservice.service.OrderService;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.UserService;
import com.druffel.foodservice.service.VoteService;
import com.druffel.foodservice.service.VotedservicesService;

/**
 * <b>Description:</b><br>
 * Controller for the startOrder view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class StartOrderController
{
    @Inject
    FoodserviceService foodserviceService;

    @Inject
    VotedservicesService votedservicesService;

    @Inject
    VoteService voteService;

    @Inject
    OrderService orderService;

    @Inject
    UserService userService;

    @Inject
    ConfigurationService configurationService;

    @Inject
    VotePhase votePhase;

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    Navigation navigator;

    public boolean createNewOrder(User currentUser, List<String> selectedServices, int phasetime, String reciept)
    {
        try
        {
            persistNewProcess(currentUser, reciept);
            setNewVotedServices(selectedServices);
            persistPhaseTime(phasetime);
            startVote();
            FacesContext.getCurrentInstance().getExternalContext().redirect("../vote.xhtml");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    protected void addMessage(FacesMessage message)
    {
        FacesContext.getCurrentInstance().addMessage("", message);
    }
    
    protected void startVote()
    {
        votePhase.start();
    }

    protected void redirect()
    {
        navigator.redirectToCurrentPhase();
    }

    protected void persistNewProcess(User currentUser, String reciept)
    {
        votedservicesService.deleteAll();
        voteService.deleteAll();
        orderService.deleteAll();
        Configuration conf = configurationService.getConfigurationByName("reciept");
        conf.setConfvalue(reciept.trim());
        configurationService.merge(conf);

        orderprocessService.startNewProcess(currentUser);
    }

    protected void setNewVotedServices(List<String> selectedServices)
    {
        votedservicesService.setNewVotedServices(selectedServices);
    }

    protected void persistPhaseTime(int phasetime)
    {
        configurationService.remove(configurationService.getConfigurationByName("time"));
        configurationService.create(new Configuration("time", Integer.toString(phasetime)));
    }

    public List<Foodservice> getAllFoodservice()
    {
        return foodserviceService.findAll();
    }

    public List<Foodservice> getFoodserviceSelect()
    {
        return foodserviceService.nonHiddenNotExludedService();
    }
}
