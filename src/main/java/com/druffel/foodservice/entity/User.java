package com.druffel.foodservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * <b>Description:</b><br>
 * 
 * Entity class for user table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "user", catalog = "foodservice")
public class User implements java.io.Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>serialVersionUID</code>
     */

    private Integer userid;

    private Role role;

    private String initials;

    private byte[] password;

    private byte[] salt;

    private String email;

    private Boolean bank;
    
    private Boolean activated;

    private List<Order> orders = new ArrayList<>(0);

    private List<PasswordResetToken> passwordresettokens = new ArrayList<>(0);

    private List<UserActivationToken> useractivationtoken = new ArrayList<>(0);
    
    private List<Orderprocess> orderprocesses = new ArrayList<>(0);

    private List<Vote> votes = new ArrayList<>(0);

    public User()
    {
    }

    public User(Role role, byte[] password, byte[] salt, String initials, boolean bank, String email)
    {
        this.role = role;
        this.password = password;
        this.salt = salt;
        this.initials = initials;
        this.bank = bank;
        this.email = email;
        this.activated = false;
    }
    
    public User(Role role, String initials, byte[] password, byte[] salt)
    {
        this.role = role;
        this.initials = initials;
        this.password = password;
        this.salt = salt;
        this.activated = false;
    }

    public User(Role role, String initials, byte[] password, byte[] salt, String email, Boolean bank, List<Order> orders,
            List<PasswordResetToken> passwordresettokens, List<Orderprocess> orderprocesses, List<Vote> votes)
    {
        this.role = role;
        this.initials = initials;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.bank = bank;
        this.orders = orders;
        this.passwordresettokens = passwordresettokens;
        this.orderprocesses = orderprocesses;
        this.votes = votes;
        this.activated = false;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "userid", unique = true, nullable = false)
    public Integer getUserid()
    {
        return this.userid;
    }

    public void setUserid(Integer userid)
    {
        this.userid = userid;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", nullable = false)
    public Role getRole()
    {
        return this.role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    @Column(name = "initials", nullable = false, length = 5)
    public String getInitials()
    {
        return this.initials;
    }

    public void setInitials(String initials)
    {
        this.initials = initials;
    }

    @Column(name = "password", nullable = false)
    public byte[] getPassword()
    {
        return this.password;
    }

    public void setPassword(byte[] password)
    {
        this.password = password;
    }

    @Column(name = "salt", nullable = false)
    public byte[] getSalt()
    {
        return this.salt;
    }

    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }

    @Column(name = "email")
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Column(name = "bank")
    public Boolean getBank()
    {
        return this.bank;
    }

    public void setBank(Boolean bank)
    {
        this.bank = bank;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Order> getOrders()
    {
        return this.orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<PasswordResetToken> getPasswordresettokens()
    {
        return this.passwordresettokens;
    }

    public void setPasswordresettokens(List<PasswordResetToken> passwordresettokens)
    {
        this.passwordresettokens = passwordresettokens;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Orderprocess> getOrderprocesses()
    {
        return this.orderprocesses;
    }

    public void setOrderprocesses(List<Orderprocess> orderprocesses)
    {
        this.orderprocesses = orderprocesses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<Vote> getVotes()
    {
        return this.votes;
    }

    public void setVotes(List<Vote> votes)
    {
        this.votes = votes;
    }

    @Column(name = "activated")
    public Boolean getActivated()
    {
        return activated;
    }

    public void setActivated(Boolean activated)
    {
        this.activated = activated;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<UserActivationToken> getUseractivationtoken()
    {
        return useractivationtoken;
    }

    public void setUseractivationtoken(List<UserActivationToken> useractivationtoken)
    {
        this.useractivationtoken = useractivationtoken;
    }

}
