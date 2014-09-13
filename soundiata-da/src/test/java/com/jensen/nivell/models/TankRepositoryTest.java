package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.jensen.nivell.assertions.RepositoryAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TankRepositoryTest {

    private Gson gson = new Gson();
    private Validator alertValidator = mock(Validator.class);
    private CouchbaseClient client = mock(CouchbaseClient.class);
    private IConnectionManager connectionManager = mock(IConnectionManager.class);
    private TankRepository repository = new TankRepository(connectionManager, gson, alertValidator);

    @Before
    public void setUp(){

        repository = new TankRepository(connectionManager, gson, alertValidator);
    }


    @Test
    public void add_method_calls_couchbase_client_set_methods_with_correct_arguments() throws Exception {

        Tank expectedTank =  new Tank(
                "12345698",
                "Sokorodji1",
                null, new BigDecimal("8.00"),
                new BigDecimal("-89.23"), new BigDecimal("-179.89"));

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(alertValidator.validate(expectedTank)).thenReturn(new HashSet<ConstraintViolation<Tank>>());
        when(client.incr("tank::count", 1)).thenReturn(12345698L);

        TankRepository repository = new TankRepository(connectionManager, gson, alertValidator);
        repository.add(expectedTank);

        assertThat(client).setMethodIsCalledWith(expectedTank.getReference(), 0, gson.toJson(expectedTank));
    }

    @Test
    public void get_method_calls_couchbase_client_get_methods_with_correct_arguments() throws Exception {

        Tank expectedTank =  new Tank(
                "12345698",
                "Sokorodji1",
                null, new BigDecimal("8.00"),
                new BigDecimal("-89.23"), new BigDecimal("-179.89"));

        CouchbaseClient client = mock(CouchbaseClient.class);
        IConnectionManager connectionManager = mock(IConnectionManager.class);

        when(connectionManager.getClientInstance()).thenReturn(client);
        when(alertValidator.validate(expectedTank)).thenReturn(new HashSet<ConstraintViolation<Tank>>());
        when(client.get(expectedTank.getLookupKey())).thenReturn("uid");
        when(client.get("uid")).thenReturn(expectedTank);

        TankRepository testRepository = new TankRepository(connectionManager, gson, alertValidator);
        String tank = testRepository.get(expectedTank.getReference());


        assertThat(tank).isEqualTo(gson.toJson(expectedTank));
    }

}
