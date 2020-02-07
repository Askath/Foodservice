/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		RegisterServiceControllerTest.java
 *
 * Created:		Sep 4, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.foodservice.entity.Foodservice;
import com.druffel.foodservice.register.RegisterServiceController;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceControllerTest
{

    @Spy
    RegisterServiceController classToTest;
    
    @Test
    public void testRegisterService()
    {
        List<Foodservice> foodserviceList = new ArrayList<>();
        foodserviceList.add(new Foodservice());
        foodserviceList.add(new Foodservice());
        
        foodserviceList.get(0).setName("test");
        foodserviceList.get(1).setName("test2");
        Mockito.doReturn(foodserviceList).when(classToTest).getAllFoodservice();
        Mockito.doNothing().when(classToTest).createFoodservice(Matchers.any(Foodservice.class));
        
        assertTrue(classToTest.registerService("test3", "", "", true, "10"));
        assertFalse(classToTest.registerService("test", "", "", true, "10"));
    }
    
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
}
