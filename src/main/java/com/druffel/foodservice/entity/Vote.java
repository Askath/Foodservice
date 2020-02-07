package com.druffel.foodservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * <b>Description:</b><br>
 * 
 * Entity class for vote table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "vote", catalog = "foodservice")
public class Vote implements java.io.Serializable
{



    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Integer voteid;

    private Foodservice foodservice;

    private Orderprocess orderprocess;

    private User user;

    public Vote()
    {
    }

    public Vote(Foodservice foodservice, Orderprocess orderprocess, User user)
    {
        this.foodservice = foodservice;
        this.orderprocess = orderprocess;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "voteid", unique = true, nullable = false)
    public Integer getVoteid()
    {
        return this.voteid;
    }

    public void setVoteid(Integer voteid)
    {
        this.voteid = voteid;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foodservice", nullable = false)
    public Foodservice getFoodservice()
    {
        return this.foodservice;
    }

    public void setFoodservice(Foodservice foodservice)
    {
        this.foodservice = foodservice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderprocess", nullable = false)
    public Orderprocess getOrderprocess()
    {
        return this.orderprocess;
    }

    public void setOrderprocess(Orderprocess orderprocess)
    {
        this.orderprocess = orderprocess;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

}
