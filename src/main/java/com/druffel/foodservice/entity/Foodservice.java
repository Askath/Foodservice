package com.druffel.foodservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * <b>Description:</b><br>
 * 
 *Entity class for Foodservice table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "foodservice", catalog = "foodservice")
public class Foodservice implements java.io.Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>serialVersionUID</code>
     */

    private Integer foodserviceid;

    private String name;

    private String link;

    private String telephone;

    private boolean hidden;

    private boolean excluded;

    private List<Orderprocess> orderprocesses = new ArrayList<>(0);

    private List<Vote> votes = new ArrayList<>(0);

    private Votedservices votedservices;
    
    private int minorderprice;

    public Foodservice()
    {
    }

    public Foodservice(String name, boolean hidden, boolean excluded, int minorderprice)
    {
        this.name = name;
        this.hidden = hidden;
        this.excluded = excluded;
        this.minorderprice = minorderprice;
    }
    
    public Foodservice(String name, String link, boolean hidden, boolean excluded, int minorderprice)
    {
        this.name = name;
        this.link = link;
        this.hidden = hidden;
        this.excluded = excluded;
        this.minorderprice = minorderprice;
    }

    public Foodservice(String name, String link, String telephone, boolean hidden, boolean excluded, List<Orderprocess> orderprocesses, List<Vote> votes,
            Votedservices votedservices)
    {
        this.name = name;
        this.link = link;
        this.telephone = telephone;
        this.hidden = hidden;
        this.excluded = excluded;
        this.orderprocesses = orderprocesses;
        this.votes = votes;
        this.votedservices = votedservices;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "foodserviceid", unique = true, nullable = false)
    public Integer getFoodserviceid()
    {
        return this.foodserviceid;
    }

    public void setFoodserviceid(Integer foodserviceid)
    {
        this.foodserviceid = foodserviceid;
    }

    @Column(name = "name", nullable = false, length = 64)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "link", nullable = false)
    public String getLink()
    {
        return this.link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    @Column(name = "telephone", length = 32)
    public String getTelephone()
    {
        return this.telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    @Column(name = "hidden", nullable = false)
    public boolean isHidden()
    {
        return this.hidden;
    }

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    @Column(name = "excluded", nullable = false)
    public boolean isExcluded()
    {
        return this.excluded;
    }

    public void setExcluded(boolean excluded)
    {
        this.excluded = excluded;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodservice")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Orderprocess> getOrderprocesses()
    {
        return this.orderprocesses;
    }

    public void setOrderprocesses(List<Orderprocess> orderprocesses)
    {
        this.orderprocesses = orderprocesses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodservice")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Vote> getVotes()
    {
        return this.votes;
    }

    public void setVotes(List<Vote> votes)
    {
        this.votes = votes;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "foodservice")
    public Votedservices getVotedservices()
    {
        return this.votedservices;
    }

    public void setVotedservices(Votedservices votedservices)
    {
        this.votedservices = votedservices;
    }

    @Column(name = "minorderprice")
    public int getMinorderprice()
    {
        return minorderprice;
    }

    public void setMinorderprice(int minorderprice)
    {
        this.minorderprice = minorderprice;
    }

}
