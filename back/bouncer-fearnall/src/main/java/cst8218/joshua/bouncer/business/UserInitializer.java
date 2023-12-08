package cst8218.joshua.bouncer.business;

import cst8218.joshua.bouncer.entity.AppUser;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Initializes and saves default users during application startup using the AppUserFacade.
 * Author: Youssef Hamzo
 */
@Startup
@Singleton
public class UserInitializer {

    @EJB
    private AppUserFacade userFacade;

    /**
     * Method to initialize three users and save them to the database.
     */
    @PostConstruct
    private void initializeUsers() {
        createAndSaveUser("admin", "123", "Admin");
        createAndSaveUser("apiUser", "123", "ApiGroup");
        createAndSaveUser("pagesUser", "123", "PagesGroup");
    }

    /**
     * Helper method to create and save a user to the database.
     */
    private void createAndSaveUser(String username, String password, String groupname) {
        AppUser user = new AppUser();
        user.setUserid(username);
        user.setPassword(password);
        user.setGroupname(groupname);
        userFacade.create(user);
    }
}
