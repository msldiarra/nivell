package com.jensen.nivell.resources;


import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.IRepository;
import com.jensen.nivell.models.Tank;
import org.apache.log4j.Logger;

import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/alert")
@Singleton
public class AlertResource implements IAlertResource {

    private static Logger logger = Logger.getLogger(AlertResource.class);
    private IRepository<Alert> repository;
    private IRepository<Tank> tankRepository;

    @Inject
    public AlertResource(IRepository<Alert> repository, IRepository<Tank> tankRepository) {
        logger.info("new AlertResource");
        this.repository = repository;
        this.tankRepository = tankRepository;
    }

    @Path("add/{tankIdentifier}/{level: [0-9]{1,}\\.{1}[0-9]{1,6}|[0-9]{1,8}}/{time: .+}")
    @POST
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public Response add(@PathParam("tankIdentifier") String tankIdentifier, @PathParam("level") BigDecimal level, @PathParam("time") String time){

        logger.info("Saving alert to Couchbase");
        Alert alert = new Alert(tankIdentifier, level, time);

        try{

            repository.add(alert);
            logger.info("Alert saved to database.");
            logger.info("Now saving alert level to Tank");

            Gson gson = new Gson();
            Tank tank = gson.getAdapter(Tank.class).fromJson(tankRepository.get(alert.getTankIdentifier()));
            tank.setCurrentLevel(alert.getLevel());
            tankRepository.add(tank);

        }catch (ValidationException e) {
            throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build());
        } catch (Exception e) {
            throw new WebApplicationException(Response.serverError().build());
        }

        return Response.ok(alert.toString()).build();
    }

    @Path("get/{tankIdentifier}")
    @GET
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("tankIdentifier") String tankIdentifier){

        logger.info("retreiving  alert");
        return repository.get(tankIdentifier);
    }


}
