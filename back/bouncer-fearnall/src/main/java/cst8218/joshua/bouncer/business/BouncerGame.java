package cst8218.joshua.bouncer.business;

import cst8218.joshua.bouncer.entity.Bouncer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;

@Startup
@Singleton
public class BouncerGame {
    private final float CHANGE_RATE = 1;

    @EJB
    private BouncerFacade bouncerFacade;

    private List<Bouncer> bouncers;

    /**
     * Game loop method. Calls the advance one frame method on all bouncers,
     * updating their positions in the database.
     */
    @PostConstruct
    private void go() {
        // Existing code for the game loop
        new Thread(() -> {
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
        }).start();
    }
}
