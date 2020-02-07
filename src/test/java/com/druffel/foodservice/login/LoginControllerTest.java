/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.controller
 * File: 		LoginControllerTest.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.login;

import static org.junit.Assert.assertTrue;

import org.bouncycastle.crypto.params.KeyParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.foodservice.entity.Role;
import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.login.LoginController;
import com.druffel.foodservice.security.encryption.PBKDF2Generator;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest
{

    @Spy
    LoginController classToTest;
    
    @Test
    public void testLoginWithNotExistingUser()
    {
        Mockito.doReturn(null).when(classToTest).findUserInDatabase(Matchers.anyString());
        Mockito.doNothing().when(classToTest).setUserInformation(Matchers.any(User.class));
        
        assertTrue(classToTest.login("asdasdasdas", "") != null);
    }
    
    @Test
    public void testLoginWithWrongPassword()
    {
        
        PBKDF2Generator gen = new PBKDF2Generator(50000);
        gen.encryptKey("password".toCharArray());
        KeyParameter key = gen.getKey();
        
        User user = new User();
        user.setActivated(false);
        user.setInitials("asd");
        user.setSalt(gen.getSalt());
        user.setPassword(key.getKey());
        user.setBank(false);
        user.setRole(new Role());
        
        Mockito.doReturn(user).when(classToTest).findUserInDatabase(Matchers.anyString());
        Mockito.doNothing().when(classToTest).setUserInformation(Matchers.any(User.class));
        
        assertTrue(classToTest.login("asd", "asd") != null);
    }
    
    @Test
    public void testLoginWithCorrectDataNotActivated()
    {
        
        PBKDF2Generator gen = new PBKDF2Generator(50000);
        gen.encryptKey("password".toCharArray());
        KeyParameter key = gen.getKey();
        
        User user = new User();
        user.setActivated(false);
        user.setInitials("asd");
        user.setSalt(gen.getSalt());
        user.setPassword(key.getKey());
        user.setBank(false);
        user.setRole(new Role());
        
        Mockito.doReturn(user).when(classToTest).findUserInDatabase(Matchers.anyString());
        Mockito.doNothing().when(classToTest).setUserInformation(Matchers.any(User.class));
        
        assertTrue(classToTest.login("asd", "password") != null);
    }
    
    @Test
    public void testLoginWithCorrectDataAndActivated()
    {
        
        PBKDF2Generator gen = new PBKDF2Generator(50000);
        gen.encryptKey("password".toCharArray());
        KeyParameter key = gen.getKey();
        
        User user = new User();
        user.setActivated(true);
        user.setInitials("asd");
        user.setSalt(gen.getSalt());
        user.setPassword(key.getKey());
        user.setBank(false);
        user.setRole(new Role());
        
        Mockito.doReturn(user).when(classToTest).findUserInDatabase(Matchers.anyString());
        Mockito.doNothing().when(classToTest).setUserInformation(Matchers.any(User.class));
        
        assertTrue(classToTest.login("asd", "password") == null);
    }


}
