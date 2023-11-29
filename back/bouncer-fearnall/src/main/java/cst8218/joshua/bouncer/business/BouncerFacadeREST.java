/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.joshua.bouncer.business;

import cst8218.joshua.bouncer.business.AbstractFacade;
import cst8218.joshua.bouncer.entity.Bouncer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A RESTful web service endpoint, used to handle CRUD operations
 * and other interactions with the Bouncer entities in the database.
 *
 * @author Joshua Fearnall
 */
@Stateless
@Path("bouncer")
public class BouncerFacadeREST extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public BouncerFacadeREST() {
        super(Bouncer.class);
    }

    /**
     * Creates and returns a new bouncer entity;
     * will not create a new entity if ID is not null
     * 
     * @param entity The entity to create
     * @return Response containing new bouncer
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createBouncer(Bouncer entity) {
        // for a non-null ID
        if (entity.getId() != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Bouncer ID must be null.")
                    .build();
        }
        
        // set values to reasonable defaults
        if (entity.getX() == null) {
            entity.setX(0);
        }
        if (entity.getY() == null) {
            entity.setY(0);
        }
        if (entity.getYVelocity() == null) {
            entity.setYVelocity(0);
        }
        
        // create and return entity
        super.create(entity);
        
        return Response
                .status(Response.Status.CREATED)
                .entity(entity)
                .build();
    }

    /**
     * Searches the database for a bouncer with the given ID
     * and updates it with new values.
     * 
     * @param id ID of the bouncer to update
     * @param entity New values to add to the bouncer
     * @return Response containing the updated bouncer
     */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Bouncer entity) {
        // if the ID doesn't exist
        Bouncer oldBouncer = super.find(id);

        if (oldBouncer == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Bouncer with the ID " + id + " does not exist.")
                    .build();
        }
          
        if (entity.getId() != null && !entity.getId().equals(id))
        {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The ID provided in the entity does not match the ID in the URL.")
                    .build();
        }
        
        // update values
        if (entity.getX() != null) {
            oldBouncer.setX(entity.getX());
        }
        if (entity.getY() != null) {
            oldBouncer.setY(entity.getY());
        }
        if (entity.getYVelocity() != null) {
            oldBouncer.setYVelocity(entity.getYVelocity());
        }
        
        super.edit(oldBouncer);
        
        return Response
                .status(Response.Status.OK)
                .entity(oldBouncer)
                .build();
    }
    
    /**
     * Searches the database for a bouncer with the given ID
     * and replaces it with the provided bouncer if it exists.
     * 
     * @param id ID of the bouncer to replace
     * @param entity New bouncer to replace the old one with
     * @return Response containing the new bouncer
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response replace(@PathParam("id") Long id, Bouncer entity) {
        // if the ID doesn't exist
        Bouncer oldBouncer = super.find(id);

        if (oldBouncer == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Bouncer with the ID " + id + " does not exist.")
                    .build();
        }
          
        if (entity.getId() != null && !entity.getId().equals(id))
        {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The ID provided in the entity does not match the ID in the URL.")
                    .build();
        }
        
        
        // update values
        if (entity.getX() != null) {
            oldBouncer.setX(entity.getX());
        }
        else {
            oldBouncer.setX(0);
        }
        if (entity.getY() != null) {
            oldBouncer.setY(entity.getY());
        }
        else {
            oldBouncer.setY(0);
        }
        
        if (entity.getYVelocity() != null) {
            oldBouncer.setYVelocity(entity.getYVelocity());
        }
        else {
            oldBouncer.setYVelocity(0);
        }
        
        super.edit(oldBouncer);
        
        return Response
                .status(Response.Status.OK)
                .entity(oldBouncer)
                .build();
    }

    /**
     * Deletes the bouncer with the given ID from the database.
     * 
     * @param id The ID of the bouncer to delete
     * @return Response containing deletion information
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        
        if (bouncer == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Bouncer with ID: " + id + " does not exist.")
                    .build();
        }
        
        super.remove(bouncer);
        
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Bouncer find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Queries the database and returns the number of active bouncers
     * 
     * @return Response object containing the number of active bouncers
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST() {
        return Response
                .ok(String.valueOf(super.count()))
                .build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
