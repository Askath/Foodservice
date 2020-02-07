package com.druffel.foodservice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <b>Description:</b><br>
 * 
 * Entity class for Passwordresettoken table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "passwordresettoken", catalog = "foodservice")
public class PasswordResetToken implements java.io.Serializable
{



    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Integer tokenid;

    private User user;

    private String token;

    private Date expirydate;

    public PasswordResetToken()
    {
    }

    public PasswordResetToken(User user, String token, Date expirydate)
    {
        this.user = user;
        this.token = token;
        this.expirydate = expirydate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "tokenid", unique = true, nullable = false)
    public Integer getTokenid()
    {
        return this.tokenid;
    }

    public void setTokenid(Integer tokenid)
    {
        this.tokenid = tokenid;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Column(name = "token")
    public String getToken()
    {
        return this.token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expirydate", length = 19)
    public Date getExpirydate()
    {
        return this.expirydate;
    }

    public void setExpirydate(Date expirydate)
    {
        this.expirydate = expirydate;
    }

}
