package com.jensen.nivell.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import com.jensen.nivell.models.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;

public class RepositoryTestModule extends AbstractModule {

    private static Logger logger = Logger.getLogger("RepositoryModule");

    @Override
    protected void configure() {
        bind(new TypeLiteral<IRepository<Alert>>() {}).to(AlertRepository.class);
        bind(new TypeLiteral<IRepository<Tank>>() {}).to(TankRepository.class);
    }

    @Provides
    public Validator provideValidator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Provides
    @Singleton
    public IConnectionManager provideClientConnectionManager(){
        return mock(IConnectionManager.class);
    }

}
