package com.jensen.nivell.models;

import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

public class TankRepository implements IRepository<Tank> {

    private static Logger logger = Logger.getLogger(TankRepository.class);
    private CouchbaseClient client;
    private Gson gson = new Gson();
    private Validator alertValidator;

    @Inject
    TankRepository(IConnectionManager connectionManager, Gson gson, Validator alertValidator) {
        this.client = connectionManager.getClientInstance();
        this.gson = gson;
        this.alertValidator = alertValidator;
    }

    @Override
    public void add(Tank tank) throws ValidationException {

        Set<ConstraintViolation<Tank>> constraintViolations = alertValidator
                .validate(tank);

        if(constraintViolations.size() > 0){
            logger.error("Tank isn't valid : " + constraintViolations.iterator().next().getMessage());
            throw new ValidationException("Tank isn't valid. " + constraintViolations.iterator().next().getMessage());
        }

        client.set(tank.getIdentifier(), 0, gson.toJson(tank));
    }

    @Override
    public String get(String identifier) {
        Object tank = client.get(identifier);
        if(tank==null){
            return null;
        }
        return gson.toJson(tank);
    }
}
