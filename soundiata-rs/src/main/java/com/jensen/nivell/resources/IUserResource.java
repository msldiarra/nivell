package com.jensen.nivell.resources;


import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

public interface IUserResource {

    public Response getAlarms(@PathParam("user") String user, @PathParam("rate") int rate);

}
