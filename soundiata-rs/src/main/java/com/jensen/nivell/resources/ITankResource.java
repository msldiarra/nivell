package com.jensen.nivell.resources;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

public interface ITankResource {

    public Response add(@PathParam("identifier") String identifier,
                        @PathParam("name") String name,
                        @PathParam("size") BigDecimal Size,
                        @PathParam("currentLevel") BigDecimal currentLevel,
                        @PathParam("latitude") BigDecimal latitude,
                        @PathParam("longitude") BigDecimal longitude);
}
