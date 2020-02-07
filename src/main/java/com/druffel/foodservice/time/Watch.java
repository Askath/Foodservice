/*
 * Project: foodservice Package: com.druffel.foodservice.bean File: Watch.java
 * Created: Sep 27, 2016 Author: AmonDruffel (Sophos Technology GmbH) Copyright:
 * (C) 2016 Sophos Technology GmbH
 */
package com.druffel.foodservice.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * <b>Description:</b><br>
 * Class containing logic for Time and Date
 *
 * @author AmonDruffel, &copy; 2016 Sophos Technology GmbH
 */
@ManagedBean
@ApplicationScoped
public class Watch
{
    private Date currentDate;

    private String dateString;

    private int timeLeft;

    @PostConstruct
    public void init()
    {
        currentDate = new Date();
        currentDate.toString();
        dateString = "";
        dateString.length();
        
        timeLeft = 600;
    }
    
    
    public Date getCurrentDate()
    {
        return new Date();
    }

    public void setCurrentDate(Date currentDate)
    {
        this.currentDate = currentDate;
    }

    public String getDateString()
    {
        Date d = getCurrentDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
        return sdf.format(d);
    }
    
    public String formatDateToString(Date d)
    {
        SimpleDateFormat sdf;
        try
        {
            sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(d);
        }
        catch(Exception e)
        {
            //
        }
        return null;
        
    }

    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }
    
    public String formatLeftTime(int time)
    {
        int i = time;
        int m = (i-(i%60)) / 60; 
        int s = i - (m*60);
        return new String(m + " Minuten " + s + " Sekunden");
    }

    public int getTimeLeft()
    {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft)
    {
        this.timeLeft = timeLeft;
    }
}
