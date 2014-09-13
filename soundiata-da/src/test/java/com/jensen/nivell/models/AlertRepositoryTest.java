package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.verification.VerificationMode;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.jensen.nivell.assertions.RepositoryAssertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class AlertRepositoryTest {

    private Gson gson = new Gson();
    private Validator alertValidator;
    private CouchbaseClient client;
    private IConnectionManager connectionManager;
    private AlertRepository repository;

    @Before
    public void setUp(){

        alertValidator = mock(Validator.class);
        client = mock(CouchbaseClient.class);
        connectionManager = mock(IConnectionManager.class);

        when(connectionManager.getClientInstance()).thenReturn(client);
        repository = new AlertRepository(connectionManager, gson, alertValidator);
    }

    @Test(expected=ValidationException.class)
    public void adding_alert_with_incorrect_parameters_fails() throws Exception {

        String expectedAlertTime = "2013-10-10 10:10:10";
        Alert expectedAlert = new Alert(null, new BigDecimal(-1.00), expectedAlertTime);

        when(alertValidator.validate(expectedAlert)).thenReturn(getConstraintViolations("Some field validation error message"));

        repository.add(expectedAlert);
    }

    @Test
    public void adding_first_alert_of_tank_adds_alert_id_to_related_lookup_document(){

        Tank expectedTank = new Tank("tankReference", "Cuve #2", new BigDecimal("100.00"), new BigDecimal("8.00"),new BigDecimal("-89.23"), new BigDecimal("-179.89"));
        expectedTank.setUid("uid");

        String alertTime = "2013-10-10 10:10:10";
        Alert alert = new Alert("tankReference", new BigDecimal(-1.00), alertTime);

        when(alertValidator.validate(alert)).thenReturn(new HashSet<ConstraintViolation<Alert>>());
        when(client.get("tank::tankReference")).thenReturn("123456789");
        when(client.get("tank::123456789::alerts")).thenReturn(null);
        when(client.incr("alert::count",1)).thenReturn(1l);

        repository.add(alert);

        /*verify(client).get("tankReference");
        verify(client).get("tank::uid::alerts");
        verify(client).add("alert::1", gson.toJson(alert));
        verify(client).add("tank::uid::alerts", "[\"alert::"+1l+"\"]");*/
    }

    @Test
    public void adding_new_alert_on_tank_adds_alert_id_to_existing_ids(){

        Tank expectedTank = new Tank("tankReference", "Cuve #2", new BigDecimal("100.00"), new BigDecimal("8.00"),new BigDecimal("-89.23"), new BigDecimal("-179.89"));
        expectedTank.setUid("uid");

        String alertTime = "2013-10-10 10:10:10";
        Alert alert = new Alert("tankReference", new BigDecimal(-1.00), alertTime);

        when(alertValidator.validate(alert)).thenReturn(new HashSet<ConstraintViolation<Alert>>());
        when(client.get("tank::tankReference")).thenReturn("123456789");
        when(client.get("tank::123456789::alerts")).thenReturn("[\"alert::uid1\"]");
        when(client.incr("alert::count",1)).thenReturn(1l);

        repository.add(alert);

      /*  verify(client).get("tankReference");
        verify(client).get("tank::uid::alerts");
        verify(client, times(1)).add("alert::1", gson.toJson(alert));
        verify(client, times(1)).replace("tank::uid::alerts", "[\"alert::uid1\",\"alert::1\"]");*/
    }

    @Test
    public void retrieving_alerts_from_tanks_returns_list_of_alerts(){

        String alertTime = "2013-10-10 10:10:10";

        Alert firstAlert = new Alert("tankReference", new BigDecimal(1.00), alertTime);
        Alert secondAlert = new Alert("tankReference", new BigDecimal(5.00), alertTime);

        Map<String, Object> expected = Maps.newHashMap();
        expected.put("alert::uid1", firstAlert);
        expected.put("alert::uid2", secondAlert);

        when(alertValidator.validate(firstAlert)).thenReturn(new HashSet<ConstraintViolation<Alert>>());
        when(client.get("tank::tankReference")).thenReturn("123456789");
        when(client.get("tank::123456789::alerts")).thenReturn("[\"alert::uid1\",\"alert::uid2\"]");
        when(client.getBulk(Lists.newArrayList("alert::uid1","alert::uid2"))).thenReturn(expected);

        String actual = repository.get("tankReference");

       /* verify(client, times(1)).get("tankReference");
        verify(client, times(1)).get("tank::uid::alerts");
        verify(client, times(1)).getBulk(Lists.newArrayList("alert::uid1", "alert::uid2"));*/
        assertThat(actual).isEqualTo(gson.toJson(Lists.newArrayList(firstAlert,secondAlert)));
    }

    private Set<ConstraintViolation<Alert>> getConstraintViolations(final String message) {

        Set<ConstraintViolation<Alert>> constraintViolations = new HashSet();

        constraintViolations.add(new ConstraintViolation<Alert>() {
            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public Alert getRootBean() {
                return null;
            }

            @Override
            public Class<Alert> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> uClass) {
                return null;
            }
        });

        return constraintViolations;
    }



}
