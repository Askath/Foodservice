/*
 * Project: foodservice Package: com.druffel.foodservice.security.auth File:
 * PasswordValidatorTest.java Created: Sep 1, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.security.auth;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorTest
{

    PasswordValidator validator;

    @Test
    public void testIsPasswordValid()
    {
        validator = new PasswordValidator();

        assertTrue(validator.isPasswordValid("7FCK7^ud"));
        assertTrue(validator.isPasswordValid("?5-6L$NCM%Qqpc6K"));
        assertFalse(validator.isPasswordValid("amonAmonasd12"));
        assertFalse(validator.isPasswordValid("amonAmonasd"));
        assertFalse(validator.isPasswordValid("amonmonasd"));
        assertFalse(validator.isPasswordValid("ASDADASDASDADADASD12,4"));
        assertFalse(validator.isPasswordValid("11739018381032103"));
        assertTrue(validator.isPasswordValid("8sa-4Tk%8JG2rYUZB?j*h3C3CYpSAMj^!qv@FUE$-f^x@_#3wDK-3u#Yr4Nny6pC=#zjbDKq&f_Xkxu"));
        assertFalse(validator.isPasswordValid("Y8h_H^Z"));
    }
}
