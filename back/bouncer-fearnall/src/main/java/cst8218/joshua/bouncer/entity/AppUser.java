/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.joshua.bouncer.entity;

import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotNull;


/**
 * Entity class representing an application user with properties for user ID, password,
 * and group name. Passwords are securely hashed using Pbkdf2PasswordHash.
 * @author Admin
 */
@Entity
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "USERID")
    private String userid;
    @Column(name = "PASSWORD")
    private String password;  
    @Column(name = "GROUPNAME")
    private String groupname; 
    
    /**
     * Getter method for retrieving the unique identifier.
     * @return The unique identifier.
     */    
    public Long getId() { 
        return id;
    }

    /**
     * Setter method for setting the unique identifier.
     * @param id The unique identifier to set.
     */
    public void setId(Long id) { 
        this.id = id;
    }

    /**
     * Getter method for retrieving the user ID.
     * @return The user ID.
     */
    public String getUserid() { 
        return userid;
    }

    /**
     * Setter method for setting the user ID.
     * @param userid The user ID to set.
     */
    public void setUserid(String userid) { 
        this.userid = userid;
    }

    /**
     * Getter method for retrieving the hashed password.
     * Note: The actual password is not returned for security reasons.
     * @return The hashed password.
     */
    public String getPassword() { 
        return ""; 
    }

    /**
     * Getter method for retrieving the group name.
     * @return The group name.
     */
    public String getGroupname() { 
        return groupname;
    }

    /**
     * Setter method for setting the group name.
     * @param groupname The group name to set.
     */
    public void setGroupname(String groupname) { 
        this.groupname = groupname;
    }

    /**
     * Setter method for the password property.
     * Hashes the provided password using Pbkdf2PasswordHash before storing it.
     * @param password The plain-text password to set.
     */

    public void setPassword(String password) {
            if(password.equals(""))
                return;
            
        // Obtain an instance of Pbkdf2PasswordHash using CDI
        Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
        PasswordHash passwordHash = instance.get();
        // Initialize the PasswordHash instance
        passwordHash.initialize(new HashMap<String,String>());
        // Generate and set the hashed password
        String passwordEntry = password; 
        passwordEntry = passwordHash.generate(passwordEntry.toCharArray());      
        this.password = passwordEntry;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.mavenproject999.AppUser[ id=" + id + " ]";
    }
    
}
