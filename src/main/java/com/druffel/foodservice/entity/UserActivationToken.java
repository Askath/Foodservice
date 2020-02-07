/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.entity
 * File: 		UserActivationToken.java
 *
 * Created:		Aug 30, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <b>Description:</b><br>
 * 
 * Entity class for useractivationtoken table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "useractivationtoken", catalog = "foodservice")
public class UserActivationToken
{

    private int useractivationtokenid;
    
    private String token;
    
    private User user;
    
    private Date expirydate;
    
    
    public UserActivationToken()
    {
        //
    }
    
    public UserActivationToken(String token, User user, Date expirydate)
    {
        this.user = user;
        this.token = token;
        this.expirydate = expirydate;
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "useractivationtokenid", unique = true, nullable = false)
    public int getUseractivationtokenid()
    {
        return useractivationtokenid;
    }

    public void setUseractivationtokenid(int useractivationtokenid)
    {
        this.useractivationtokenid = useractivationtokenid;
    }

    @Column(name = "token", length = 255, nullable = false)
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expirydate", length = 19)
    public Date getExpirydate()
    {
        return expirydate;
    }

    public void setExpirydate(Date expirydate)
    {
        this.expirydate = expirydate;
    }
    
}
