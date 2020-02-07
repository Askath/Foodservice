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
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * <b>Description:</b><br>
 * 
 * Entity class for role table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "role", catalog = "foodservice")
public class Role implements java.io.Serializable
{

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>serialVersionUID</code>
     */

    private Integer roleid;

    private String name;

    private List<User> users = new ArrayList<>(0);

    public Role()
    {
    }

    public Role(String name)
    {
        this.name = name;
    }

    public Role(String name, List<User> users)
    {
        this.name = name;
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "roleid", unique = true, nullable = false)
    public Integer getRoleid()
    {
        return this.roleid;
    }

    public void setRoleid(Integer roleid)
    {
        this.roleid = roleid;
    }

    @Column(name = "name", nullable = false, length = 24)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<User> getUsers()
    {
        return this.users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

}
