package com.jensen.nivell.assertions;

import org.fest.assertions.api.AbstractAssert;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class ConstraintViolationAssert<T extends ConstraintViolationAssert<T>> extends AbstractAssert<ConstraintViolationAssert<T>, Set<ConstraintViolation<T>>> {

    public ConstraintViolationAssert(Set<ConstraintViolation<T>> actual) {
        super(actual, ConstraintViolationAssert.class);
    }

    public T hasSize(int size) {
        isNotNull();
        assertThat(actual.size()).isEqualTo(size);
        return (T) this;
    }

    public T hasErrorMessage(String message) {
        isNotNull();
        assertThat(extractProperty("message", String.class).from(actual)).contains(message);
        return (T) this;
    }
}
