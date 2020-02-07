/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		ActivateUserControllerTest.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.register;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.entity.UserActivationToken;
import com.druffel.foodservice.register.ActivateUserController;

@RunWith(MockitoJUnitRunner.class)
public class ActivateUserControllerTest
{

    @Spy
    ActivateUserController classToTest;
    
    @Test
    public void testActivateUserWithEmptytoken()
    {
        assertFalse(classToTest.activateUser(null));
        assertFalse(classToTest.activateUser(""));
    }
    
    @Test
    public void testActivateUserWithExpiredToken()
    {
        
        String tokenid = UUID.randomUUID().toString();
        
        UserActivationToken token = new UserActivationToken();
        token.setExpirydate(new Date(new Date().getTime() - 60000));
        token.setToken(tokenid);
        token.setUser(new User());
        
        Mockito.doReturn(token).when(classToTest).findTokenByTokenString(Matchers.anyString());
        Mockito.doNothing().when(classToTest).mergeUser(Matchers.any(User.class));
        Mockito.doNothing().when(classToTest).removeToken(Matchers.any(UserActivationToken.class));
        
        assertFalse(classToTest.activateUser(tokenid));
        
    }
    
    @Test
    public void testActivateUserWithNotFoundToken()
    {
        
        String tokenid = UUID.randomUUID().toString();
        
        UserActivationToken token = new UserActivationToken();
        token.setExpirydate(new Date(new Date().getTime() + 60000));
        token.setToken(tokenid);
        token.setUser(new User());
        
        Mockito.doReturn(null).when(classToTest).findTokenByTokenString(Matchers.anyString());
        Mockito.doNothing().when(classToTest).mergeUser(Matchers.any(User.class));
        Mockito.doNothing().when(classToTest).removeToken(Matchers.any(UserActivationToken.class));
        
        assertFalse(classToTest.activateUser(UUID.randomUUID().toString()));
        
    }

    @Test
    public void testActivateUser()
    {
        
        String tokenid = UUID.randomUUID().toString();
        
        UserActivationToken token = new UserActivationToken();
        token.setExpirydate(new Date(new Date().getTime() + 60000));
        token.setToken(tokenid);
        token.setUser(new User());
        
        Mockito.doReturn(token).when(classToTest).findTokenByTokenString(Matchers.anyString());
        Mockito.doNothing().when(classToTest).mergeUser(Matchers.any(User.class));
        Mockito.doNothing().when(classToTest).removeToken(Matchers.any(UserActivationToken.class));
        
        assertTrue(classToTest.activateUser(tokenid));
        
    }
    
}
