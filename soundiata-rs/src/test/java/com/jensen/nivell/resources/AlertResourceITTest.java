package com.jensen.nivell.resources;

import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.Tank;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertResourceITTest extends ResourceITTemplate {

    private static Alert expectedAlertWithCorrectParameters;
    private static Tank expectedTank;
    private Gson gson = new Gson();

    @BeforeClass
    public static void setUp() throws IOException {
        client = mock(CouchbaseClient.class);

        testDate = "2013-10-10 10:10:10";
        expectedAlertWithCorrectParameters = new Alert("12349875", new BigDecimal("8.00"), testDate);
        expectedTank = new Tank(
                "12349875",
                "Sokorodji1",
                null, new BigDecimal("8.00"),
                new BigDecimal("-89.23"), new BigDecimal("-179.89"));

        startService();
        webResource = webResource();
    }

    @AfterClass
    public static void tearDown(){
        client.shutdown();
        server.stop();
    }

    @Test
    public void calling_add_with_valid_parameters_returns_response_status_200() {

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(client.get(expectedTank.getIdentifier())).thenReturn((Object) expectedTank);

        ClientResponse response = webResource.path("alert/add/12349875/8.00/" + testDate)
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with valid parameters should return 200 OK",
                response.getClientResponseStatus().toString(), is(Response.Status.OK.toString()));

        verify(client).set(expectedAlertWithCorrectParameters.getPersistenceKey(), 0, gson.toJson(expectedAlertWithCorrectParameters));
        verify(client).set(expectedTank.getPersistenceKey(), 0, gson.toJson(expectedTank));
    }

    @Test
    public void calling_add_with_invalid_date_parameter_returns_response_status_400() {

        when(connectionManager.getClientInstance()).thenReturn(client);

        ClientResponse response = webResource.path("alert/add/12349875/8.00/" + "2013-12-89")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with invalid time parameter should return 400 Bad Request",
                response.getClientResponseStatus().toString(), is(Response.Status.BAD_REQUEST.toString()));

        assertThat("Calling add with invalid time parameter should return correct error message",
                response.getEntity(String.class).toString(),
                is("Alert isn't valid. Time value is null or incorrect; correct format is yyyy-MM-dd hh:mm:ss"));
    }

    @Test
    public void calling_add_with_invalid_level_parameter_returns_response_status_404() {

        when(connectionManager.getClientInstance()).thenReturn(client);

        ClientResponse response = webResource.path("alert/add/12349875/8./" + "2013-10-10 10:10:10")
                .accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        assertThat("Calling add with invalid level parameter should return 404 Not Found",
                response.getClientResponseStatus().toString(), is(Response.Status.NOT_FOUND.toString()));
    }


    @Test
    public void calling_get_with_valid_parameters_returns_alert() throws IOException {

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(client.get("12349875")).thenReturn(expectedAlertWithCorrectParameters);

        String alert = webResource.path("alert/get/12349875").accept("text/plain").get(String.class);

        assertThat(alert, is(gson.toJson(expectedAlertWithCorrectParameters)));
    }
}
