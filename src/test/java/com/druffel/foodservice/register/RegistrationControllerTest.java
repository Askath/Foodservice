/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		RegistrationControllerTest.java
 *
 * Created:		Sep 4, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.faces.application.FacesMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.foodservice.entity.Role;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.register.RegistrationController;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest
{

    @Spy
    RegistrationController classToTest;
    
    @Test
    public void testRegisterUserUserExists()
    {
        User user = new User();
        user.setInitials("username");
        
        Mockito.doNothing().when(classToTest).addMessage(Matchers.any(FacesMessage.class));
        Mockito.doReturn(user).when(classToTest).lookForUser(Matchers.anyString());
        Mockito.doNothing().when(classToTest).sendActivationMail(Matchers.any(User.class), Matchers.anyString());
        
        assertFalse(classToTest.register("username", "asd", "asd", true, "asd"));
        
    }
    
    @Test
    public void testRegisterUser()
    {
        User user = new User();
        user.setInitials("username");
        
        Mockito.doNothing().when(classToTest).addMessage(Matchers.any(FacesMessage.class));
        Mockito.doReturn(null).when(classToTest).lookForUser(Matchers.anyString());
        Mockito.doNothing().when(classToTest).sendActivationMail(Matchers.any(User.class), Matchers.anyString());
        Mockito.doNothing().when(classToTest).persistUser(Matchers.any(User.class));
        Mockito.doReturn(new Role()).when(classToTest).getUserRole();
        
        assertTrue(classToTest.register("username", "asd", "asd", true, "asd"));
        
    }
    
    @Test
    public void testRegisterUserPasswordNotEqual()
    {
        User user = new User();
        user.setInitials("username");
        
        Mockito.doNothing().when(classToTest).addMessage(Matchers.any(FacesMessage.class));
        Mockito.doReturn(null).when(classToTest).lookForUser(Matchers.anyString());
        Mockito.doNothing().when(classToTest).sendActivationMail(Matchers.any(User.class), Matchers.anyString());
        Mockito.doNothing().when(classToTest).persistUser(Matchers.any(User.class));
        Mockito.doReturn(new Role()).when(classToTest).getUserRole();
        
        assertFalse(classToTest.register("username", "asd", "aasd", true, "asd"));
        
    }
    
}
