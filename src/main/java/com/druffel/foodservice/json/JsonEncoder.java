/*
 * Project: foodservice Package: com.druffel.foodservice.json File:
 * JsonEncoder.java Created: Oct 17, 2016 Author: AmonDruffel (Sophos Technology
 * GmbH) Copyright: (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.json.simple.JSONObject;

import com.druffel.foodservice.entity.User;
import com.druffel.foodservice.service.OrderprocessService;

/**
 * <b>Description:</b><br>
 * Encoding Order data into Json file
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
public class JsonEncoder
{
    // removed JSON parser

    @Inject
    OrderprocessService orderprocessFacade;

    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private String filename;

    private JSONObject orderObj;

    String jsonString;

    byte[] data;

    public JsonEncoder(String filename)
    {
        this.filename = filename;
    }

    public JsonEncoder()
    {
        //
    }

    /**
     * Generates Json from orders
     * 
     * @param label Name of service
     * @param userList List of all Users
     * @param sum sum of all prices
     * @param prices HashMap with all prices per user
     * @throws IOException 
     */
    public void generateJson(String label, List<User> userList, double sum, Map<String, Double> prices) throws IOException
    {
        
        logger.debug("(GenerateJson())");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T12:00:00+0200'");

        orderObj = new JSONObject();
        orderObj.put("label", label);
        orderObj.put("date", sdf.format(new Date()));

        JSONObject values = new JSONObject();
        orderObj.put("values", values);

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00", otherSymbols);
        double notBank = 0;
        for (User u : userList)
        {
            if (u.getBank())
            {
                if (prices.get(u.getInitials()) != 0.0)
                {
                    values.put(u.getInitials(), Double.parseDouble(df.format(prices.get(u.getInitials()) / 100)));
                }
            }
            else
            {
                notBank = notBank + prices.get(u.getInitials());
            }
        }

        orderObj.put("sum", Double.parseDouble(df.format((sum - notBank) / 100)));

        jsonString = orderObj.toJSONString();
        logger.debug("Created Json String: " + jsonString);
        generateFile();

    }

    public void generateFile() throws IOException
    {
        
        logger.debug("(generateFile())");
        
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));

        try
        {
            out.write(orderObj.toJSONString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            out.close();
        }
        File file = new File(filename);
        if(!file.exists())
        {
            file.delete();
        }
        logger.debug("created file " + file.getName());


    }

    /**
     * @return Get byte array of Json Object to save in DB
     */
    public byte[] getData()
    {
        data = orderObj.toJSONString().getBytes();
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public String getJsonString()
    {
        return jsonString;
    }

    public void setOrderObj(JSONObject object)
    {
        this.orderObj = object;
    }

    public JSONObject getOrderObj()
    {
        return this.orderObj;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getFilename()
    {
        return this.filename;
    }

    public void setJsonString(String json)
    {
        this.jsonString = json;
    }

}