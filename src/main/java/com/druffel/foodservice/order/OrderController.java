/*
 * Project: foodservice Package: com.druffel.foodservice.controller File:
 * OrderController.java Created: Aug 18, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.order;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.druffel.foodservice.entity.Configuration;
import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.entity.Order;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.phase.OrderPhase;
import com.druffel.foodservice.service.ConfigurationService;
import com.druffel.foodservice.service.OrderService;
import com.druffel.foodservice.service.OrderprocessService;
import com.druffel.foodservice.service.UserService;
import com.druffel.foodservice.service.VotedservicesService;
import com.vdurmont.emoji.EmojiParser;

/**
 * <b>Description:</b><br>
 * Controller for order view and phase.
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Dependent
public class OrderController
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    ConfigurationService configService;
    
    @Inject
    OrderService orderService;

    @Inject
    OrderprocessService orderprocessService;

    @Inject
    UserService userService;

    @Inject
    VotedservicesService votedservicesService;

    @Inject
    OrderPhase orderPhase;

    public Order findOrder(int id)
    {
        return orderService.find(id);
    }

    public boolean mergeOrder(Order o)
    {
        try
        {
            orderService.merge(o);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public boolean isSameUser(String initials, User user)
    {
        if (!user.getInitials().equals(initials))
        {
            return false;
        }
        return true;
    }

    public void deleteOrder(Order o)
    {
        orderService.remove(o);

    }

    public void persistOrder(User currentUser, String food, int price, String other)
    {
        Order order = new Order(orderprocessService.getLast(), currentUser, food, price, other);
        orderService.create(order);
    }

    protected String removeEmoji(String food)
    {
        String foodText = EmojiParser.removeAllEmojis(food);
        foodText = Normalizer.normalize(food, Normalizer.Form.NFKC);
        return foodText;
    }

    public FacesMessage addOrder(String username, String food, String priceString, String other)
    {
        User u = getUserWithInitials(username);
        String foodText = removeEmoji(food);
        String t = foodText.replace(" ", "");
        if (t.equals("") || foodText.startsWith(" "))
        {
            return new FacesMessage("Bitte gültigen Namen eingeben");
        }

        String s = priceString.replace(',', '.');

        int price = priceToText(s);
        if (!food.matches("[\\sa-zA-ZöäüÜÄ;Öß?´`+&!,\\.\\s-\\d]*$"))
        {
            return new FacesMessage("Nicht erlaubte Sonderzeichen gefunden!");
        }
        persistOrder(u, foodText, price, other);
        logger.info("added order for " + username + ": " + food + "price: " + price);
        return null;
    }

    protected int priceToText(String priceText)
    {
        int price = 0;

        if (priceText.contains("."))
        {
            if (priceText.split("\\.")[1].length() == 1)
            {
                price = Integer.parseInt(priceText.replaceAll("\\.", "")) * 10;
            }
            else
            {
                price = Integer.parseInt(priceText.replaceAll("\\.", ""));
            }
        }
        else
        {
            price = Integer.parseInt(priceText.replaceAll("\\.", "")) * 100;
        }

        return price;
    }

    public FacesMessage addOrder(User user, String food, String priceString, String other)
    {

        String foodText = food;
        foodText = EmojiParser.removeAllEmojis(food);
        foodText = Normalizer.normalize(food, Normalizer.Form.NFKC);

        foodText = foodText.trim();

        String t = foodText.replace(" ", "");
        if (t.equals(""))
        {
            return new FacesMessage("Bitte gültigen Namen eingeben");
        }

        String s = priceString.replace(',', '.');

        int price = priceToText(s);
        if (!food.matches("[\\sa-zA-ZöäüÜÄ#;Öß?´`+&!,\\.\\s-\\d]*$"))
        {
            return new FacesMessage("Nicht erlaubte Sonderzeichen gefunden!");
        }
        persistOrder(user, foodText, price, other);
        refreshOrderList();
        logger.info(user.getInitials() + "added order: " + food + "price: " + price);
        return null;
    }

    protected void refreshOrderList()
    {
        orderPhase.refresh();
    }

    public List<Order> getOrderList(User user)
    {
        List<Order> orderedList = new ArrayList<>();
        List<Order> orderList = orderPhase.getOrderList();

        for (Order o : orderedList)
        {
            if (o.getUser().getInitials().equals(user.getInitials()))
            {
                orderedList.add(o);
            }
        }

        orderList.remove(orderedList);
        orderList.stream().sequential().sorted((o1, o2) -> o1.getUser().getInitials().compareTo(o2.getUser().getInitials()));
        orderList.addAll(0, orderedList);

        return orderList;
    }

    public List<Order> getAllOrders()
    {
        return orderPhase.getOrderList();
    }

    public User getUserWithInitials(String username)
    {
        return userService.findByInitials(username);
    }

    public double getPriceSumForUser(User user)
    {
        return userService.getPriceSumForUser(user);
    }

    public List<Order> getOrderListOfUser(User currentUser)
    {
        return userService.getOrderListOfUser(currentUser);
    }

    public Map<String, Double> getPriceMap()
    {
        return userService.getPriceMapOfUser();
    }

    public Foodservice getVotedService()
    {
        return orderPhase.getVotedFoodservice();
    }

    public String getReciept()
    {
        return configService.getConfigurationByName("reciept").getConfvalue().trim();
    }

    public void appointReciept(User user)
    {
        Configuration config = configService.getConfigurationByName("reciept");
        config.setConfvalue(user.getInitials());
        configService.merge(config);
    }
}
