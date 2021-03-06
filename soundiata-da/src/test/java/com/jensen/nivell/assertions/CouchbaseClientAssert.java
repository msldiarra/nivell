package com.jensen.nivell.assertions;

import com.couchbase.client.CouchbaseClient;
import org.fest.assertions.api.AbstractAssert;
import org.mockito.Mockito;

public class CouchbaseClientAssert<T extends CouchbaseClientAssert> extends AbstractAssert<CouchbaseClientAssert<T>, CouchbaseClient> {

    public CouchbaseClientAssert(CouchbaseClient actual){
        super(actual, CouchbaseClientAssert.class);
    }

    public T setMethodIsCalledWith(String tankReference, int ttl, String alert) {
        isNotNull();
        Mockito.verify(actual).set(tankReference, ttl, alert);
        return (T) this;
    }

    public T getMethodIsCalledWith(String identifier) {
        isNotNull();
        Mockito.verify(actual).get(identifier);
        return (T) this;
    }

    public T getAlarmsMethodIsCalledWith(String user){
        isNotNull();
        Mockito.verify(actual).getView("tank", "alarms_by_user");
        return (T) this;
    }
}
