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
        
    public Long getId() { //getter method for retrieving the unique identifier
        return id;
    }

    public void setId(Long id) { //Setter method for setting the unique identifier.
        this.id = id;
    }

    public String getUserid() { //Getter method for retrieving the user ID.
        return userid;
    }

    public void setUserid(String userid) { //Setter method for setting the user ID.
        this.userid = userid;
    }

    public String getPassword() { //Getter method for retrieving the hashed password.
        return ""; //dont want to return actual password!!
    }

        public String getGroupname() { //Getter method for retrieving the group name.
        return groupname;
    }

    public void setGroupname(String groupname) { //Setter method for setting the group name.
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
