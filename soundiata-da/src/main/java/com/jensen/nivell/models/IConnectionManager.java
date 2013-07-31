package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;

public interface IConnectionManager {
    public CouchbaseClient getClientInstance();
    public void closeClientConnection();
}
