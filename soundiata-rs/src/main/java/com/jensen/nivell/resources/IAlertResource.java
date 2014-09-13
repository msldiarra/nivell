package com.jensen.nivell.resources;


import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;

public interface IAlertResource {

    public Response add(@PathParam("tankReference") String tankReference, @PathParam("level") BigDecimal level,@PathParam("time") String time);
}
