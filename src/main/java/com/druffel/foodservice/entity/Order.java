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
 * Entity class for Order table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "order", catalog = "foodservice")
public class Order implements java.io.Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>serialVersionUID</code>
     */

    private Integer orderid;

    private Orderprocess orderprocess;

    private User user;

    private String food;

    private int price;

    private String other;

    public Order()
    {
    }

    public Order(Orderprocess orderprocess, User user, String food, int price)
    {
        this.orderprocess = orderprocess;
        this.user = user;
        this.food = food;
        this.price = price;
    }

    public Order(Orderprocess orderprocess, User user, String food, int price, String other)
    {
        this.orderprocess = orderprocess;
        this.user = user;
        this.food = food;
        this.price = price;
        this.other = other;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "orderid", unique = true, nullable = false)
    public Integer getOrderid()
    {
        return this.orderid;
    }

    public void setOrderid(Integer orderid)
    {
        this.orderid = orderid;
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

    @Column(name = "food", nullable = false, length = 64)
    public String getFood()
    {
        return this.food;
    }

    public void setFood(String food)
    {
        this.food = food;
    }

    @Column(name = "price", nullable = false)
    public int getPrice()
    {
        return this.price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    @Column(name = "other")
    public String getOther()
    {
        return this.other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

}
