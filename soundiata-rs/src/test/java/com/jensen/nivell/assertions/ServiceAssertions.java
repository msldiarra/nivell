package com.jensen.nivell.assertions;

import com.couchbase.client.CouchbaseClient;
import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.AlertRepository;
import com.jensen.nivell.models.TankRepository;
import org.fest.assertions.api.Assertions;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ServiceAssertions extends Assertions {

    public static AlertRepositoryAssert assertThat(AlertRepository actual) {
        return new AlertRepositoryAssert(actual);
    }

    public static TankRepositoryAssert assertThat(TankRepository actual) {
        return new TankRepositoryAssert(actual);
    }
}

