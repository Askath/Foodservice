/*
 * Project: foodservice Package: com.druffel.foodservice.security.auth File:
 * PasswordValidator.java Created: Aug 28, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.security.auth;

import java.util.regex.Pattern;

/**
 * <b>Description:</b><br>
 * 
 * Class for validating password restrictions
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
public class PasswordValidator
{
    public boolean isPasswordValid(String password)
    {

        boolean uppercase = false;
        boolean lowercase = false;
        boolean special = false;
        boolean number = false;

        int minLenghth = 8;

        Pattern p;

        if (password.length() > minLenghth -1)
        {
            for (char c : password.toCharArray())
            {
                if (Character.isLowerCase(c))
                {
                    lowercase = true;
                }

                if (Character.isUpperCase(c))
                {
                    uppercase = true;
                }

                if (Character.isDigit(c))
                {
                    number = true;
                }

                p = Pattern.compile("[^a-zA-Z1-9]");
                if (p.matcher(password).find())
                {
                    special = true;
                }

            }

            if (uppercase && lowercase && special && number)
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return false;
        }

    }
}
