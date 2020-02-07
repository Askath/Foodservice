/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * OrderStatusManagedBean.java Created: Dec 1, 2016 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.orderStatus;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Orderprocess;
import com.druffel.foodservice.phase.VotePhase;

/**
 * <b>Description:</b><br>
 * 
 *Managed bean for the orderStatus view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class OrderStatusManagedBean implements Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Inject
    OrderStatusController controller;
    
    @Inject
    VotePhase votePhase;

    private Date deliveryTime;

    private Foodservice votedService;

    private Orderprocess orderprocess;

    @PostConstruct
    public void init()
    {
        votedService = controller.getVotedService();
        deliveryTime = controller.getDeliveryTime();
        orderprocess = controller.getOrderprocess();
    }

    public void downloadJson() throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect("getJson");
    }
    
    public void sendJson() throws ParseException, IOException
    {
        controller.sendJson();
    }
    
    public String getJsonString()
    {
        return controller.getJsonString();
    }

    public void appointNewDeliveryTime() throws IOException
    {
        controller.appointNewDeliveryTime();
        FacesContext.getCurrentInstance().getExternalContext().redirect("waitingForOrder.xhtml");
    }

    public void orderEnd()
    {
        controller.orderEnd(deliveryTime);
    }

    public Foodservice getVotedService()
    {
        return votedService;
    }

    public void setVotedService(Foodservice votedService)
    {
        this.votedService = votedService;
    }

    public String getServerAdress()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String prefix = (req.isSecure()) ? "https://" : "http://";
        return new String(prefix + req.getLocalAddr() + ":" + req.getLocalPort());
    }

    public boolean isWaitingForOrder()
    {
        return controller.isWaitingForOrder();
    }

    public boolean isWaitingForDelivery()
    {
        return controller.isWaitingForDelivery();
    }

    public boolean isDelivered()
    {
        return controller.isDelivered();
    }

    public void ordered(HashMap<String, Double> priceMap, double sum)
    {
        controller.ordered(priceMap, sum, deliveryTime);
    }

    public Date getDeliveryTime()
    {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }

    public Orderprocess getOrderprocess()
    {
        return orderprocess;
    }

    public void setOrderprocess(Orderprocess orderprocess)
    {
        this.orderprocess = orderprocess;
    }

}
