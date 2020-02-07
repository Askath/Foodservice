/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * StartOrderManagedBean.java Created: Dec 1, 2016 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.order;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.User;

/**
 * <b>Description:</b><br>
 * 
 * Managed bean for the startOrder view
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@ManagedBean
@RequestScoped
public class StartOrderManagedBean
{
    @Inject
    StartOrderController controller;

    private List<Foodservice> foodserviceSelect;
    
    private String reciept;

    @NotNull(message = "Wählen sie bitte mindestens einen Lieferservice aus.")
    private List<String> selectedServices;

    @NotNull(message = "Bitte Phasenzeit angeben.")
    int phasetime;

    @PostConstruct
    public void init()
    {
        foodserviceSelect = controller.getFoodserviceSelect();
    }

    public int getPhasetime()
    {
        return phasetime;
    }

    public boolean createNewOrder(User currentUser)
    {
        return controller.createNewOrder(currentUser, selectedServices, phasetime, reciept);
    }

    public List<String> getSelectedServices()
    {
        return selectedServices;
    }

    public void setSelectedServices(List<String> selectedServices)
    {
        this.selectedServices = selectedServices;
    }

    public void setPhasetime(int phasetime)
    {
        this.phasetime = phasetime;
    }

    public List<Foodservice> getFoodserviceSelect()
    {
        return foodserviceSelect;
    }

    public void setFoodserviceSelect(List<Foodservice> foodserviceSelect)
    {
        this.foodserviceSelect = foodserviceSelect;
    }

    public String getReciept()
    {
        return reciept;
    }

    public void setReciept(String reciept)
    {
        this.reciept = reciept;
    }
    
    

}
