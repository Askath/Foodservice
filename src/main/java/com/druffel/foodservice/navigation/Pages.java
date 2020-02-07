/*
 * Project:		foodservice
 * Package:		com.druffel.foodservice.navigation
 * File: 		Pages.java
 *
 * Created:		Oct 9, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.navigation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("pages")
@RequestScoped
public class Pages
{

    //Orderprocess
    public static final String ORDER_PHASE = "content/order.xhtml";
    public static final String VOTE_PHASE = "content/vote.xhtml";
    public static final String ORDER_STATUS = "content/orderStatus.xhtml";
    public static final String NO_ORDER = "content/noOrder.xhtml";
    public static final String WAITING_FOR_ORDER = "content/waitingForOrder.xhtml";
    public static final String ORDERED = "content/ordered.xhtml";
    public static final String DELIVERED = "content/delivered.xhtml";
    
    //restricted pages
    public static final String REGISTER_SERVICE = "content/admin/registerService.xhtml";
    public static final String START_ORDER = "content/admin/startOrder.xhtml";
    
    //public pages
    public static final String ACTIVATE_USER = "activateUser.xhtml";
    public static final String EXPIRED = "expired.xhtml";
    public static final String FOODPLANS = "foodplans.xhtml";
    public static final String HOME = "index.xhtml";
    public static final String REGISTER = "register.xhtml";
    
    //reset password
    public static final String FORGOTT_PASSWORD = "resetPassword/forgottPassword.xhtml";
    public static final String RESET_PASSWORD = "resetPassword/resetPassword.xhtml";
    
    //util
    public static final String ABSOLUTE = "/";
    
}
