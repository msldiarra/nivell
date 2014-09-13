package com.jensen.nivell.resources;


import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class UserResource implements  IUserResource{

    @Override
    public Response getAlarms(@PathParam("user") String user, @PathParam("rate") int rate) {
        return null;
    }
}
