package com.druffel.foodservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * <b>Description:</b><br>
 * 
 * Entity class for Orderprocess table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "orderprocess", catalog = "foodservice")
public class Orderprocess implements java.io.Serializable
{



    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Integer orderprocessid;

    private Foodservice foodservice;

    private User user;

    private Date starttime;

    private Date orderstarttime;

    private Date orderendtime;

    private Date orderedtime;

    private Date deliveredtime;

    private Boolean aborted;

    private Date deliveryplannedtime;

    private byte[] json;

    private List<Order> orders = new ArrayList<>(0);

    private List<Vote> votes = new ArrayList<>(0);

    public Orderprocess()
    {
    }

    public Orderprocess(User user, Date starttime)
    {
        this.user = user;
        this.starttime = starttime;
    }

    public Orderprocess(Foodservice foodservice, User user, Date starttime, Date orderstarttime, Date orderendtime, Date orderedtime, Date deliveredtime,
            Boolean aborted, Date deliveryplannedtime, byte[] json, List<Order> orders, List<Vote> votes)
    {
        this.foodservice = foodservice;
        this.user = user;
        this.starttime = starttime;
        this.orderstarttime = orderstarttime;
        this.orderendtime = orderendtime;
        this.orderedtime = orderedtime;
        this.deliveredtime = deliveredtime;
        this.aborted = aborted;
        this.deliveryplannedtime = deliveryplannedtime;
        this.json = json;
        this.orders = orders;
        this.votes = votes;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "orderprocessid", unique = true, nullable = false)
    public Integer getOrderprocessid()
    {
        return this.orderprocessid;
    }

    public void setOrderprocessid(Integer orderprocessid)
    {
        this.orderprocessid = orderprocessid;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foodservice")
    public Foodservice getFoodservice()
    {
        return this.foodservice;
    }

    public void setFoodservice(Foodservice foodservice)
    {
        this.foodservice = foodservice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderinguser", nullable = false)
    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "starttime", nullable = false, length = 19)
    public Date getStarttime()
    {
        return this.starttime;
    }

    public void setStarttime(Date starttime)
    {
        this.starttime = starttime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderstarttime", length = 19)
    public Date getOrderstarttime()
    {
        return this.orderstarttime;
    }

    public void setOrderstarttime(Date orderstarttime)
    {
        this.orderstarttime = orderstarttime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderendtime", length = 19)
    public Date getOrderendtime()
    {
        return this.orderendtime;
    }

    public void setOrderendtime(Date orderendtime)
    {
        this.orderendtime = orderendtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderedtime", length = 19)
    public Date getOrderedtime()
    {
        return this.orderedtime;
    }

    public void setOrderedtime(Date orderedtime)
    {
        this.orderedtime = orderedtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deliveredtime", length = 19)
    public Date getDeliveredtime()
    {
        return this.deliveredtime;
    }

    public void setDeliveredtime(Date deliveredtime)
    {
        this.deliveredtime = deliveredtime;
    }

    @Column(name = "aborted")
    public Boolean getAborted()
    {
        return this.aborted;
    }

    public void setAborted(Boolean aborted)
    {
        this.aborted = aborted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deliveryplannedtime", length = 19)
    public Date getDeliveryplannedtime()
    {
        return this.deliveryplannedtime;
    }

    public void setDeliveryplannedtime(Date deliveryplannedtime)
    {
        this.deliveryplannedtime = deliveryplannedtime;
    }

    @Column(name = "json")
    public byte[] getJson()
    {
        return this.json;
    }

    public void setJson(byte[] json)
    {
        this.json = json;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderprocess")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Order> getOrders()
    {
        return this.orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderprocess")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Vote> getVotes()
    {
        return this.votes;
    }

    public void setVotes(List<Vote> votes)
    {
        this.votes = votes;
    }

}
