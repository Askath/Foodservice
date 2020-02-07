package com.druffel.foodservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * <b>Description:</b><br>
 * 
 * Entity model for the configuration table
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Entity
@Table(name = "configuration", catalog = "foodservice")
public class Configuration implements java.io.Serializable
{



    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Integer configurationid;

    private String confkey;

    private String confvalue;

    public Configuration()
    {
    }

    public Configuration(String confkey, String confvalue)
    {
        this.confkey = confkey;
        this.confvalue = confvalue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "configurationid", unique = true, nullable = false)
    public Integer getConfigurationid()
    {
        return this.configurationid;
    }

    public void setConfigurationid(Integer configurationid)
    {
        this.configurationid = configurationid;
    }

    @Column(name = "confkey", nullable = false, length = 64)
    public String getConfkey()
    {
        return this.confkey;
    }

    public void setConfkey(String confkey)
    {
        this.confkey = confkey;
    }

    @Column(name = "confvalue", nullable = false, length = 64)
    public String getConfvalue()
    {
        return this.confvalue;
    }

    public void setConfvalue(String confvalue)
    {
        this.confvalue = confvalue;
    }

}
