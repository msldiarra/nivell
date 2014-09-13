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

    @Path("add/{tankReference}/{level: [0-9]{1,}\\.{1}[0-9]{1,6}|[0-9]{1,8}}/{time: .+}")
    @POST
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public Response add(@PathParam("tankReference") String tankReference, @PathParam("level") BigDecimal level, @PathParam("time") String time){

        logger.info("Saving alert to Couchbase");
        Alert alert = new Alert(tankReference, level, time);

        try{

            repository.add(alert);
            logger.info("Alert saved to database.");
            logger.info("Now saving alert level to Tank");

            Gson gson = new Gson();
            //String tankUid = tankRepository.get(tankReference);
            Tank tank = gson.fromJson( tankRepository.get(tankReference), Tank.class);
            tank.setCurrentLevel(level);
            tankRepository.add(tank);

        }catch (ValidationException e) {
            logger.error(e.getMessage(),e);
            throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new WebApplicationException(Response.serverError().build());
        }

        return Response.ok(alert.toString()).build();
    }

    @Path("alerts/{tankReference}")
    @GET
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("tankReference") String tankReference){

        logger.info("retreiving  alerts foe tank");
        return repository.get(tankReference);
    }


}
