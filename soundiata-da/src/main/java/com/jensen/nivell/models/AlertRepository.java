package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.*;

public class AlertRepository implements IRepository<Alert> {

    private static Logger logger = Logger.getLogger(AlertRepository.class);
    private CouchbaseClient client;
    private Gson gson = new Gson();
    private Validator alertValidator;
    private String tankUid;

    @Inject
    AlertRepository(IConnectionManager connectionManager, Gson gson, Validator alertValidator) {
        this.client = connectionManager.getClientInstance();
        this.gson = gson;
        this.alertValidator = alertValidator;
    }

    /**
     *
     * @param tankReference
     * @return all alerts for a specific tank using lookup pattern
     */
    public String get(String tankReference) {

        List<String> uids = alertsUidList("tank::" + tankReference);

        Collection<Object> alerts = client.getBulk(uids).values();

        return gson.toJson(alerts);
    }

    /**
     *
     * @param alert
     * @throws ValidationException
     * An alert is associated with a tank in database
     * by creating a predictibale key witch refers to the tank uid
     */
    public void add(Alert alert) throws ValidationException {

        Set<ConstraintViolation<Alert>> constraintViolations = alertValidator
                .validate(alert);

        if(constraintViolations.size() > 0){
            logger.error("Alert isn't valid : " + constraintViolations.iterator().next().getMessage());
            throw new ValidationException("Alert isn't valid. " + constraintViolations.iterator().next().getMessage());
        }


        // Saving new alert
        Long alertUid = client.incr("alert::count", 1);
        client.add("alert::" + alertUid, gson.toJson(alert));

        // Adding this alert uid to tank referenced by alert
        List<String> ids = alertsUidList(alert.getTankReference());

        // adding related alerts to tank
        if(tankUid != null && (ids == null || ids.isEmpty())) {
            client.add(tankUid + "::alerts", "[\"alert::" + alertUid + "\"]");
        } else {
            ids.add("alert::" + alertUid);
            client.replace(tankUid + "::alerts", gson.toJson(ids));
        }

    }

    public CouchbaseClient getClient() {
        return client;
    }

    public void setClient(CouchbaseClient client) {
        this.client = client;
    }

    private List<String> alertsUidList(String tankReference) {

        // retreiving tank id with lookup key (reference)
        tankUid = (String) client.get(tankReference);
        String alerts_ids = (String) client.get("tank::" + tankUid + "::alerts");

        return gson.fromJson(alerts_ids, new TypeToken<List<String>>(){}.getType());
    }

}
