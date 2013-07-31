package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.logging.Logger;

public class RepositoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<IRepository<Alert>>() {}).to(AlertRepository.class);
        bind(new TypeLiteral<IRepository<Tank>>() {}).to(TankRepository.class);
        //bind(IConnectionManager.class).to(ClientConnectionManager.class).in(Singleton.class);
    }

    @Provides
     public Validator provideValidator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
