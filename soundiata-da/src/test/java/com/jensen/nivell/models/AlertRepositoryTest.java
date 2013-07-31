package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.jensen.nivell.assertions.RepositoryAssertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlertRepositoryTest {

    private Gson gson = new Gson();
    private Validator alertValidator;

    @Before
    public void setUp(){
        alertValidator = mock(Validator.class);
    }

    @Test
    public void add_method_calls_couchbase_client_set_methods_wit_correct_arguments() throws Exception {

        String expectedAlertTime = "2013-10-10 10:10:10";
        Alert expectedAlert = new Alert("1", new BigDecimal(1.00), expectedAlertTime);

        CouchbaseClient client = mock(CouchbaseClient.class);
        IConnectionManager connectionManager = mock(IConnectionManager.class);
        when(connectionManager.getClientInstance()).thenReturn(client);
        when(alertValidator.validate(expectedAlert)).thenReturn(new HashSet<ConstraintViolation<Alert>>());

        AlertRepository testRepository = new AlertRepository(connectionManager, gson, alertValidator);
        testRepository.add(expectedAlert);

        assertThat(client).setMethodIsCalledWith(expectedAlert.getPersistenceKey(), 0, gson.toJson(expectedAlert));
    }

    @Test(expected=ValidationException.class)
    public void adding_alert_with_incorrect_parameters_fails() throws Exception {

        String expectedAlertTime = "2013-10-10 10:10:10";
        Alert expectedAlert = new Alert(null, new BigDecimal(-1.00), expectedAlertTime);

        CouchbaseClient client = mock(CouchbaseClient.class);
        IConnectionManager connectionManager = mock(IConnectionManager.class);
        when(connectionManager.getClientInstance()).thenReturn(client);
        when(alertValidator.validate(expectedAlert)).thenReturn(getConstraintViolations("Some field validation error message"));

        AlertRepository testRepository = new AlertRepository(connectionManager, gson, alertValidator);
        testRepository.add(expectedAlert);
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
