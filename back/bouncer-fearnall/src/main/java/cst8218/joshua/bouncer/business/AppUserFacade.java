/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.joshua.bouncer.business;


import cst8218.joshua.bouncer.business.AbstractFacade;
import cst8218.joshua.bouncer.entity.AppUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Facade for managing AppUser entities.
 * @author Youssef Hamzo
 */
@Stateless
public class AppUserFacade extends AbstractFacade<AppUser> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppUserFacade() {
        super(AppUser.class);
    }
    
}
