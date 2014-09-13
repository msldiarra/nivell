package com.jensen.nivell.resources;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.jensen.nivell.models.Tank;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TankResourceITTest extends ResourceITTemplate {

    private static Tank expectedTank;
    private Gson gson = new Gson();

    @BeforeClass
    public static void setUp() throws IOException {
        client = mock(CouchbaseClient.class);

        expectedTank = new Tank(
                "12349875",
                "Sokorodji1",
                new BigDecimal("5000"), new BigDecimal("8.00"),
                new BigDecimal("-89.23"), new BigDecimal("-179.89"));
        startService();
        webResource = webResource();
    }

    @Before
    public void getResource(){

    }

    @AfterClass
    public static void tearDown(){
        client.shutdown();
        server.stop();
    }

    @Test
    public void calling_add_with_valid_parameters_returns_response_status_200() {

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(client.incr("tank::count", 1)).thenReturn(12349875L);

        ClientResponse response = webResource.path("tank/add/12349875/Sokorodji1/5000/8.00/-89.23/-179.89")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with valid parameters should return 200 OK",
                response.getClientResponseStatus().toString(), is(Response.Status.OK.toString()));

        verify(client).set("12349875", 0, gson.toJson(expectedTank));
    }

    @Test
    public void calling_add_with_invalid_level_parameter_returns_response_status_404() {

        when(connectionManager.getClientInstance()).thenReturn(client);

        ClientResponse response = webResource.path("tank/add/12349875/Sokorodji1/5000/8./-89.23/-179.89")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with invalid parameter time should return 404 NOT FOUND",
                response.getClientResponseStatus().toString(), is(Response.Status.NOT_FOUND.toString()));
    }

    @Test
    public void calling_add_with_invalid_size_parameter_returns_response_status_400() {

        when(connectionManager.getClientInstance()).thenReturn(client);

        ClientResponse response = webResource.path("tank/add/12349875/Sokorodji1/-5000/8.00/-89.23/-179.89")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with invalid parameter time should return 400 Bad Request",
                response.getClientResponseStatus().toString(), is(Response.Status.BAD_REQUEST.toString()));

        assertThat("Calling add with invalid size parameter should return correct error message",
                response.getEntity(String.class).toString(),
                is("Tank isn't valid. Size must be a positive value"));
    }

    @Test
    public void calling_add_with_invalid_latitude_parameter_returns_response_status_400() {

        when(connectionManager.getClientInstance()).thenReturn(client);

        ClientResponse response = webResource.path("tank/add/12349875/Sokorodji1/5000/8.00/-102.23/-179.89")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with invalid parameter time should return 400 Bad Request",
                response.getClientResponseStatus().toString(), is(Response.Status.BAD_REQUEST.toString()));

        assertThat("Calling add with invalid latitude parameter should return correct error message",
                response.getEntity(String.class).toString(),
                is("Tank isn't valid. Latitude must be greater than -90.00"));
    }

    @Test
    public void calling_add_with_invalid_longitude_parameter_returns_response_status_400() {

        when(connectionManager.getClientInstance()).thenReturn(client);

        ClientResponse response = webResource.path("tank/add/12349875/Sokorodji1/5000/8.00/-56.23/-195.89")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with invalid parameter time should return 400 Bad Request",
                response.getClientResponseStatus().toString(), is(Response.Status.BAD_REQUEST.toString()));

        assertThat("Calling add with invalid longitude parameter should return correct error message",
                response.getEntity(String.class).toString(),
                is("Tank isn't valid. Longitude must be greater than -180.00"));
    }

    @Test
    public void calling_get_with_valid_parameters_returns_alert() throws IOException {

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(client.get("tank::12349875")).thenReturn("tankUid");
        when(client.get("tankUid")).thenReturn(expectedTank);

        String tank = webResource.path("tank/get/12349875").accept("text/plain").get(String.class);

        assertThat(tank, is(gson.toJson(expectedTank)));
    }

    @Test
    public void calling_get_with_valid_parameters_returns_no_resource() throws IOException {

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(client.get("tank::12349875")).thenReturn(null);

        ClientResponse response = webResource.path("tank/get/12349875").accept("text/plain").get(ClientResponse.class);

        assertThat("Calling add with invalid parameter time should return 404 NOT FOUND",
                response.getClientResponseStatus().toString(), is(Response.Status.NOT_FOUND.toString()));
    }

}
