package com.jensen.nivell.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jensen.nivell.models.*;

import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import org.apache.log4j.Logger;

@Path("tank")
@Singleton
public class TankResource implements ITankResource {

    private static Logger logger = Logger.getLogger("TankResource");
    private IRepository<Tank> repository;

    @Inject
    public TankResource(IRepository<Tank> repository) {
        logger.info("new TankResource");
        this.repository = repository;
    }

    @Path("add/{identifier}/{name: .+}/{size}/{level: [0-9]{1,}\\.{1}[0-9]{1,6}|[0-9]{1,8}}/{latitude: .+}/{longitude: .+}")
    @POST
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public Response add(@PathParam("identifier") String identifier, @PathParam("name") String name,@PathParam("size") BigDecimal size, @PathParam("level") BigDecimal currentLevel, @PathParam("latitude") BigDecimal latitude, @PathParam("longitude") BigDecimal longitude) {

        logger.info("Saving tank to Couchbase");
        Tank tank = new Tank(identifier, name, size, currentLevel, latitude, longitude);

        try{
            repository.add(tank);
        }catch (ValidationException e) {
            throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build());
        } catch (Exception e) {
            throw new WebApplicationException(Response.serverError().build());
        }

        return Response.ok(tank.toString()).build();
    }

    @Path("get/{identifier}")
    @GET
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("identifier") String identifier){

        logger.info("retreiving  tank with identifier : " + identifier);
        String tank = repository.get(identifier);

        if(tank == null) {
            throw new WebApplicationException(Response.status(404).build());
        }

        return tank;
    }

    /*@Path("user/{user}/alarms")
    @GET
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAlarms(@PathParam("user") String user){

        logger.info("retreiving alarms for user : " + user);

        String tanks = repository.getAlarms(user);

        return tanks;
    }*/
}
