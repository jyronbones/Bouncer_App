/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.joshua.bouncer.business;

import cst8218.joshua.bouncer.entity.AppUser;
import cst8218.joshua.bouncer.entity.Bouncer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Singleton EJB used to run the game loop for the bouncer application.
 * 
 * Calls go method, which creates a thread that updates all the bouncers.
 * 
 * @author Joshua Fearnall
 */
@Startup
@Singleton
public class BouncerGame {
    private final float CHANGE_RATE = 1;

    @EJB
    private cst8218.joshua.bouncer.business.BouncerFacade bouncerFacade;

    @EJB
    private cst8218.joshua.bouncer.business.AppUserFacade userFacade;

    private List<Bouncer> bouncers;


    /**
     * Game loop method. Calls the advance one frame method on all bouncers,
     * updating their positions in the database.
     */
    @PostConstruct
    private void go() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    bouncers = bouncerFacade.findAll();
                    for (Bouncer bouncer : bouncers) {
                        bouncer.advanceOneFrame();
                        bouncerFacade.edit(bouncer);
                    }
                    try {
                        Thread.sleep((long)(1.0 / CHANGE_RATE * 1000));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}