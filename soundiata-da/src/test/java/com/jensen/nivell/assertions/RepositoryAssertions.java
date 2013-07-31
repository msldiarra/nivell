package com.jensen.nivell.assertions;

import com.couchbase.client.CouchbaseClient;
import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.Tank;
import org.fest.assertions.api.Assertions;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class RepositoryAssertions extends Assertions {

    public static AlertAssert assertThat(Alert actual) {
        return new AlertAssert(actual);
    }

    public static TankAssert assertThat(Tank actual){
        return new TankAssert(actual);
    }

    public static ConstraintViolationAssert assertThat(Set<ConstraintViolation<Alert>> actual) {
        return new ConstraintViolationAssert(actual);
    }

    public static CouchbaseClientAssert assertThat(CouchbaseClient actual) {
        return new CouchbaseClientAssert(actual);
    }


}
