/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.joshua.bouncer.business;

import cst8218.joshua.bouncer.business.AbstractFacade;
import cst8218.joshua.bouncer.entity.Bouncer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB class that provides a stateless session bean for bouncer management,
 * extends AbstractFacade to allow for use of CRUD operations.
 * 
 * Serves as an intermediary, allowing you to edit Bouncer entities.
 *
 * @author Joshua Fearnall
 */
@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BouncerFacade() {
        super(Bouncer.class);
    }
    
}
