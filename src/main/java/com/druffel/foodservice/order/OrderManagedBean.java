/*
 * Project: foodservice Package: com.druffel.foodservice.bean File:
 * OrderManagedBean.java Created: Oct 6, 2016 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

import org.primefaces.event.RowEditEvent;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Order;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.navigation.Navigation;

/**
 * <b>Description:</b><br>
 * Managedbean for Order Entity and Order related logic
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
@Named
@RequestScoped
public class OrderManagedBean implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Inject
    Navigation navigator;
    
    @Inject
    OrderController controller;

    private String other;

    private String food;

    @Pattern(regexp = "^$|^\\d{1,2}(,\\d{1,2}|.\\d{1,2})?$", message = "Bitte geben sie einen Gültigen Dezimalwert an. Bsp 12,31.")
    private String priceString;

    private Double price;

    private List<Order> orderList;

    private boolean orderMode = false;

    private boolean changeScreen = false;

    private int orderId;

    private double sum;
    
    private String reciept;

    private double sumForUser;
    
    @Pattern(regexp = "^[A-Za-z]{2,4}$", message = "Kürzel darf nur aus max 2-4 Buchstaben bestehen.")
    private String username;

    private Map<String, Double> priceMap = new HashMap<>();

    @PostConstruct
    public void init()
    {
        reciept = controller.getReciept();
        orderList = controller.getAllOrders();
    }
    
    public boolean isReachedMinOrderPrice()
    {
        try
        {
            return ((int) getSum() < controller.getVotedService().getMinorderprice()) ? true:false;
        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return true;
    }
    
    public void setOrderMode(int id, boolean b)
    {
        orderId = id;
        this.orderMode = b;
    }
    
    public void appointReciept(User user)
    {
        controller.appointReciept(user);
    }

    public void onRowEdit(RowEditEvent event) {
        controller.mergeOrder((Order) event.getObject());
    }
     
    public void onRowCancel(RowEditEvent event) {
       event.getObject();
    }
    
    public Order findOrder(int id)
    {
       return controller.findOrder(id);
    }

    public boolean mergeOrder(Order o)
    {
        return controller.mergeOrder(o);
    }

    public boolean isSameUser(String initials, User user)
    {
        return controller.isSameUser(initials, user);
    }

    public void deleteOrder(Order o)
    {
        controller.deleteOrder(o);
    }

    public boolean addOrder(User currentUser)
    {
        FacesMessage message = controller.addOrder(currentUser, food, priceString, other); 
        if(message != null)
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        clearInput();
        return true;
    }

    public void clearInput()
    {
        food = "";
        priceString = "";
        other = "";
    }

    public boolean addOrder()
    {
        FacesMessage message = controller.addOrder(username, food, priceString, other);
        if(message != null)
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        clearInput();
        return true;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

    public String getFood()
    {
        return food;
    }

    public void setFood(String food)
    {
        this.food = food;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public List<Order> getOrderList(User user)
    {
        return controller.getOrderList(user);
    }
    
    public String getServerAdress()
    {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String prefix = (req.isSecure()) ? "https://":"http://";
        return new String(prefix + req.getLocalAddr() + ":" + req.getLocalPort());
    }

    public List<Order> getOrderList()
    {
        return orderList;
    }

    public void setOrderList(List<Order> orderList)
    {
        this.orderList = orderList;
    }

    public List<Order> getOrderListOfUser(User currentUser)
    {
        return controller.getOrderListOfUser(currentUser);
    }

    public boolean isOrderMode()
    {
        return orderMode;
    }

    public void setOrderMode(boolean orderMode)
    {
        this.orderMode = orderMode;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public boolean isChangeScreen()
    {
        return changeScreen;
    }

    public void setChangeScreen(boolean changeScreen)
    {
        this.changeScreen = changeScreen;
    }

    /**
     * @return Sum of all Orders
     */
    public double getSum()
    {
        sum = 0D;
        for (Order o : controller.getAllOrders())
        {
            sum = sum + o.getPrice();
        }
        return sum;
    }

    public void setSum(double sum)
    {
        this.sum = sum;
    }

    public double getSumForUser(User user)
    {
        sumForUser = controller.getPriceSumForUser(user);
        return sumForUser;
    }

    public void setSumForUser(double sumForUser)
    {
        this.sumForUser = sumForUser;
    }

   
    public Map<String, Double> getPriceMap()
    {
        priceMap = controller.getPriceMap();
       return priceMap;
    }

    public void setPriceMap(HashMap<String, Double> priceMap)
    {
        this.priceMap = priceMap;
    }

    public String getPriceString()
    {
        return priceString;
    }

    public void setPriceString(String priceString)
    {
        this.priceString = priceString;
    }

    public Foodservice getVotedService()
    {
        return controller.getVotedService();
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getOther()
    {
        return other;
    }

    public String getReciept()
    {
        
        return reciept;
    }

    public void setReciept(String reciept)
    {
        this.reciept = reciept;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}
