/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		RegisterServiceController.java
 *
 * Created:		Aug 25, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.service.FoodserviceService;
import com.druffel.foodservice.service.OrderprocessService;

/**
 * <b>Description:</b><br>
 * 
 * Controller for the registerService view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class RegisterServiceController
{

    @Inject
    private FoodserviceService foodserviceService;
    
    @Inject
    OrderprocessService orderprocessService;
    
    public boolean registerService(String name, String link, String telephone, boolean excluded, String minorderprice)
    {
        
        String linkw = link;
        
        for(Foodservice service : getAllFoodservice())
        {
            if(service.getName().equals(name))
            {
               return false;
            }
        }
        
        Foodservice fs = new Foodservice(name, false, excluded, priceToText(minorderprice));
        if(link.length() < 5)
        {
            linkw = "resources/img/placeholder.png";
        }
        fs.setLink(linkw);
        fs.setTelephone(telephone);
       createFoodservice(fs);
       return true;
    }
    
    protected void createFoodservice(Foodservice fs)
    {
        foodserviceService.create(fs);
    }
    
    protected List<Foodservice> getAllFoodservice()
    {
        return foodserviceService.findAll();
    }
    
    protected int priceToText(String priceText)
    {
        int price = 0;
        
        if(priceText.contains("."))
        {
            if(priceText.split("\\.")[1].length() == 1)
            {
                price = Integer.parseInt(priceText.replaceAll("\\.", "")) * 10;
            }
            else if(priceText.split("\\.")[1].length() == 2)
            {
                price = Integer.parseInt(priceText.replaceAll("\\.", ""));
            }
        }else
        {
            price = Integer.parseInt(priceText.replaceAll("\\.", "")) * 100;
        }
        
        return price;
    }

    
}
