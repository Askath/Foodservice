/*
 * Project: foodservice Package: com.druffel.foodservice.encrypt File:
 * PBKDF2Generator.java Created: Sep 27, 2016 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.security.encryption;

import java.security.SecureRandom;
import java.util.Random;

import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * <b>Description:</b><br>
 * Class to hash passwort into PBKDF2 format
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
public class PBKDF2Generator
{

    private PKCS5S2ParametersGenerator generator;

    private KeyParameter key;

    private int iterationCount;

    private byte[] salt;

    public PBKDF2Generator(int iterationCount)
    {
        generator = new PKCS5S2ParametersGenerator();
        this.iterationCount = iterationCount;
    }

    /**
     * Constructor
     * @param iterationCount    
     * @param salt              Salt of existing user in Database
     */
    public PBKDF2Generator(int iterationCount, byte[] salt)
    {
        generator = new PKCS5S2ParametersGenerator();
        this.iterationCount = iterationCount;
        this.salt = salt;
    }

    /**
     * @param password      plain password in char array
     */
    public void encryptKey(char[] password)
    {
        generator.init(PBEParametersGenerator.PKCS5PasswordToBytes(password), getSalt(), iterationCount*2);
        key = (KeyParameter) generator.generateDerivedParameters(128*8);
    }

    public KeyParameter getKey()
    {
        return key;
    }

    
    /** Generating random salt if not already set
     * @return
     */
    public byte[] getSalt()
    {
        if (salt == null)
        {
            final Random r = new SecureRandom();
            salt = new byte[32];
            r.nextBytes(salt);
        }

        return salt;
    }

}
