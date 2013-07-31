package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

public class AlertRepository implements IRepository<Alert> {

    private static Logger logger = Logger.getLogger(AlertRepository.class);
    private CouchbaseClient client;
    private Gson gson = new Gson();
    private Validator alertValidator;

    @Inject
    AlertRepository(IConnectionManager connectionManager, Gson gson, Validator alertValidator) {
        this.client = connectionManager.getClientInstance();
        this.gson = gson;
        this.alertValidator = alertValidator;
    }

    public String get(String key) {
        return gson.toJson(client.get(key));
    }

    public void add(Alert alert) throws ValidationException {

        Set<ConstraintViolation<Alert>> constraintViolations = alertValidator
                .validate(alert);

        if(constraintViolations.size() > 0){
            logger.error("Alert isn't valid : " + constraintViolations.iterator().next().getMessage());
            throw new ValidationException("Alert isn't valid. " + constraintViolations.iterator().next().getMessage());
        }

        client.set(alert.getPersistenceKey(), 0, gson.toJson(alert));
    }

    public CouchbaseClient getClient() {
        return client;
    }

    public void setClient(CouchbaseClient client) {
        this.client = client;
    }


}
