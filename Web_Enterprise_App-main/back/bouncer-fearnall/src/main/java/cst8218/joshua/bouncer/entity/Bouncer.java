/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.joshua.bouncer.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class used to model the bouncer entity object in the bouncer project.
 * Contains behaviors for moving the bouncer object, and fields that contain
 * the objects position and velocity.
 * 
 * @author Joshua Fearnall
 */
@Entity
@XmlRootElement
public class Bouncer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final int GRAVITY_ACCEL = 1;
    private final int DECAY_RATE = 1;
    private final int FRAME_HEIGHT = 600;
    private final int FRAME_WIDTH = 800;
    private Integer x;
    private Integer y;
    private Integer yVelocity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Retrieves the bouncers ID
     * 
     * @return The bouncers ID as a Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the bouncers ID to the given value
     * 
     * @param id New ID for the bouncer
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Retrieves the bouncers X position
     * 
     * @return The bouncers X position as an integer
     */
    public Integer getX() {
        return x;
    }
    
    /**
     * Sets the bouncers X position to the given value
     * 
     * @param x New X position for the bouncer
     */
    public void setX(Integer x) {
        this.x = x;
    }
    
    /**
     * Retrieves the bouncers Y position
     * 
     * @return The Y position of the bouncer
     */
    public Integer getY() {
        return y;
    }
    
    /**
     * Sets the bouncers Y position to the given value
     * 
     * @param y New Y position for the bouncer
     */
    public void setY(Integer y) {
        this.y = y;
    }
    
    /**
     * Retrieves the bouncers Y velocity
     * 
     * @return The Y velocity for the bouncer
     */
    public Integer getYVelocity() {
        return yVelocity;
    }
    
    /**
     * Sets the bouncers Y velocity to the given value
     * 
     * @param yVelocity New Y velocity for the bouncer
     */
    public void setYVelocity(Integer yVelocity) {
        this.yVelocity = yVelocity;
    }
    
    /**
     * Handles movement that takes place during game play,
     * moving the bouncer in the Y direction each time we advance one frame.
     */
    public void advanceOneFrame()
    {      
        // add gravity
        if (y != FRAME_HEIGHT || yVelocity != 0) {
            yVelocity += GRAVITY_ACCEL;
        }
        
        // move bouncer
        y += yVelocity;
        
        // reached wall
        if (y <= 0 || y >= FRAME_HEIGHT) {
            // reverse velocity
            yVelocity *= -1;
            
            // add decay
            if (yVelocity != 0) {
                yVelocity += (y <= 0) ? -DECAY_RATE : DECAY_RATE;
            }
            
            // keep bouncer in bounds
            if (y <= 0) {
                y = 0;
            }
            else {
                y = FRAME_HEIGHT;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.joshua.bouncer.Bouncer[ id=" + id + " ]";
    }
    
}
