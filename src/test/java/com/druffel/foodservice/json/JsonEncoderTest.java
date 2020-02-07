/*
 * Project: foodservice Package: com.druffel.foodservice.json File:
 * JsonEncoderTest.java Created: Sep 1, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.json;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.foodservice.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class JsonEncoderTest
{

    @Spy
    JsonEncoder jsonEncoder;

    @Test
    public void testGeneratedJson() throws ParseException, IOException
    {
        Mockito.doNothing().when(jsonEncoder).generateFile();
        
        jsonEncoder.setFilename("json.json");

        String jsonString;

        String label = "marco";

        List<User> userList = new ArrayList<>();
        User userOne = new User();
        userOne.setInitials("ad");
        userOne.setBank(true);

        User userTwo = new User();
        userTwo.setInitials("test");
        userTwo.setBank(false);

        userList.add(userTwo);
        userList.add(userOne);

        Double sum = 20d;

        HashMap<String, Double> priceMap = new HashMap<>();
        priceMap.put(userOne.getInitials(), 2000d);
        priceMap.put(userTwo.getInitials(), 600d);

        jsonEncoder.generateJson(label, userList, sum, priceMap);

        jsonString = jsonEncoder.getJsonString();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonString);
        JSONObject jsonObject = (JSONObject) obj;

        JSONObject values = (JSONObject) jsonObject.get("values");
        for (User u : userList)
        {
            if (u.getBank())
            {
                sum = sum - (double) values.get(u.getInitials());
            }
        }
        assertTrue(sum == 0);

    }
}
