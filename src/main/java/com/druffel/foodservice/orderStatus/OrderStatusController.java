/*
 * Project: foodservice Package: com.druffel.foodservice.controller File:
 * OrderStatusController.java Created: Aug 25, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.orderStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Orderprocess;
import com.druffel.foodservice.json.JsonEncoder;
import com.druffel.foodservice.mail.Mailer;
import com.druffel.foodservice.navigation.Navigation;
import com.druffel.foodservice.phase.OrderStatusPhase;
import com.druffel.foodservice.security.auth.LoginStatus;
import com.druffel.foodservice.service.ConfigurationService;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.UserService;
import com.druffel.foodservice.service.VoteService;
import com.druffel.foodservice.service.VotedservicesService;

/**
 * <b>Description:</b><br>
 * 
 * Controller for OrderStatus view.
 * 
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class OrderStatusController
{

    @Inject
    VotedservicesService votedservicesService;

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    UserService userService;

    @Inject
    VoteService voteService;

    @Inject
    ConfigurationService configurationService;
    
    @Inject
    LoginStatus loginStatus;
    
    @Inject
    OrderStatusPhase orderStatusPhase;
    
    @Inject
    Navigation navigator;
    
    public void appointNewDeliveryTime()
    {
        orderprocessService.appointNewDeliveryTime();
        orderStatusPhase.refresh();
    }

    public void orderEnd(Date deliveryTime)
    {
        appointNewDeliveryTime();
        orderprocessService.orderEnd();
        orderprocessService.setDeliveryTime(deliveryTime);
        orderStatusPhase.refresh();
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("delivered.xhtml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void ordered(HashMap<String, Double> priceMap, double sum, Date deliveryTime)
    {
        try
        {
            Orderprocess op = orderprocessService.getLast();

            op.setDeliveryplannedtime(deliveryTime);
            op.setOrderedtime(new Date());
            orderprocessService.merge(op);
            JsonEncoder jsonenc = new JsonEncoder("json.json");
            jsonenc.generateJson(votedservicesService.getLast().getFoodservice().getName(), userService.findAll(), sum, priceMap);
            op.setJson(jsonenc.getData());
            orderprocessService.merge(op);
            orderStatusPhase.refresh();
            FacesContext.getCurrentInstance().getExternalContext().redirect("ordered.xhtml");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("time", new FacesMessage("Ungültige Zeit"));
        }
    }
    
    public void sendJson() throws ParseException, IOException
    {
        Orderprocess op = orderprocessService.getLast();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JsonEncoder jsonenc = new JsonEncoder("auszahlung-" + sdf.format(orderprocessService.getLast().getOrderedtime()) + ".json");
    
        jsonenc.setJsonString(new String(op.getJson()));
        jsonenc.setOrderObj((JSONObject) new JSONParser().parse(jsonenc.getJsonString()));
        jsonenc.generateFile();
        
        File file = new File("auszahlung-" + sdf.format(op.getOrderedtime()) + ".json");
        FileWriter fw = new FileWriter(file);
        fw.write(new String(op.getJson()));
        fw.flush();
        fw.close();
        
        String[] rec = new String[2];
        rec[0] = configurationService.getConfigurationByName("bank_email").getConfvalue();
        rec[1] = loginStatus.getCurrentUser().getEmail();
        Mailer mailer = new Mailer("10.75.194.253", 25, "", "", "dialogssoftware@googlemail.com", "Foodservice");
        mailer.sendHtmlMail("Essensbestellung " + sdf.format(orderprocessService.getLast().getOrderedtime()), jsonenc.getJsonString(), rec, "Foodservice", file);
    }
    
    public String getJsonString()
    {
        Orderprocess op = orderprocessService.getLast();
        return new String(op.getJson());
        
    }

    public boolean isWaitingForOrder()
    {
        if (getOrderprocess().getOrderedtime() == null)
        {
            return true;
        }
        return false;
    }

    public boolean isWaitingForDelivery()
    {
        if (getOrderprocess().getDeliveredtime() == null && getOrderprocess().getOrderedtime() != null)
        {
            return true;
        }
        return false;
    }

    public boolean isDelivered()
    {
        if (getOrderprocess().getDeliveredtime() != null)
        {
            return true;
        }
        return false;
    }

    public Foodservice getVotedService()
    {
        return orderStatusPhase.getVotedService();
    }

    public Orderprocess getOrderprocess()
    {
        return orderStatusPhase.getOrderProcess();
    }

    public Date getDeliveryTime()
    {
        return orderStatusPhase.getDeliveryTime();
    }

}
