package com.cars.domainobject.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cars.domainobject.DomainObject;

/*
* DO for API_Users
*/
@Entity
@Table(name = "API_user",
        uniqueConstraints = @UniqueConstraint(name = "uc_user_username", columnNames = {"username"})
)
public class UserDO implements DomainObject
{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", length = 50, unique = true)
    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @Column(name = "password", length = 100)
    @NotNull
    @Size(min = 5, max = 200)
    private String password;

    /*
    * a User can have several roles
    */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "ID")})
    @Enumerated(EnumType.STRING)
    private List<RoleDO> roleDOs;

    public Long getId()
    {
        return id;
    }

    @Override
    public void setDeleted(boolean deleted)
    {
        //DO NOTHING
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()

    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<RoleDO> getRoleDOs()
    {
        return roleDOs;
    }
}