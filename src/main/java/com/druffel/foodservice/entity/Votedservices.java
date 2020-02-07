package com.druffel.foodservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * <b>Description:</b><br>
 * 
 * Entity class for votedservices table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "votedservices", catalog = "foodservice")
public class Votedservices implements java.io.Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>serialVersionUID</code>
     */

    private int votedservicesid;

    private Foodservice foodservice;

    public Votedservices()
    {
    }

    public Votedservices(Foodservice foodservice)
    {
        this.foodservice = foodservice;
        this.votedservicesid = foodservice.getFoodserviceid();
    }

    @Id
    @Column(name = "votedservicesid", unique = true, nullable = false)
    public int getVotedservicesid()
    {
        return this.votedservicesid;
    }

    public void setVotedservicesid(int votedservicesid)
    {
        this.votedservicesid = votedservicesid;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    public Foodservice getFoodservice()
    {
        return this.foodservice;
    }

    public void setFoodservice(Foodservice foodservice)
    {
        this.foodservice = foodservice;
    }

}
