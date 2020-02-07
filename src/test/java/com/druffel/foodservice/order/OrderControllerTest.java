/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		OrderControllerTest.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.order.OrderController;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest
{

    @Spy
    OrderController classToTest;
    
    @Test
    public void testPriceToTextTest()
    {
        String price = "12.3";
        int result = 1230;
        
        assertEquals(result, classToTest.priceToText(price));
        
        price = "12";
        result = 1200;
        assertEquals(result, classToTest.priceToText(price));
        
        price = "12.00";
        assertEquals(result, classToTest.priceToText(price));
        
        price = "12.0";
        assertEquals(result, classToTest.priceToText(price));
        
    }
    
    @Test
    public void testAddOrderWithUsername()
    {
        User user = new User();
        
        Mockito.doReturn(user).when(classToTest).getUserWithInitials(Matchers.anyString());
        Mockito.doNothing().when(classToTest).persistOrder(Matchers.any(User.class), Matchers.anyString(), Matchers.anyInt(), Matchers.anyString());
   
        assertTrue(classToTest.addOrder("ad", "food", "2.5", "test") == null);
        assertFalse(classToTest.addOrder("ad", "", "2.5", "test") == null);
        assertFalse(classToTest.addOrder("ad", " ", "2.5", "test") == null);
        assertFalse(classToTest.addOrder("ad", "<script>/asd<", "2.5", "test") == null);
        assertFalse(classToTest.addOrder("ad", "<script>/asd<", "2.5", "<ASd>s") == null);
    }
    
    @Test
    public void testAddOrderWithUser()
    {
        User user = new User();
        
        Mockito.doNothing().when(classToTest).persistOrder(Matchers.any(User.class), Matchers.anyString(), Matchers.anyInt(), Matchers.anyString());
        Mockito.doNothing().when(classToTest).refreshOrderList();
        
        assertTrue(classToTest.addOrder(user, "food", "2.5", "test") == null);
        assertFalse(classToTest.addOrder(user, "", "2.5", "test") == null);
        assertFalse(classToTest.addOrder(user, " ", "2.5", "test") == null);
        assertFalse(classToTest.addOrder(user, "<script>/asd<", "2.5", "test") == null);
        assertFalse(classToTest.addOrder(user, "<script>/asd<", "2.5", "<ASd>s") == null);
    }

}
