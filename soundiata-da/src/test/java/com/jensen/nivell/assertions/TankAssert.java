package com.jensen.nivell.assertions;

import com.jensen.nivell.models.Tank;
import org.fest.assertions.api.AbstractAssert;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

public class TankAssert<T extends TankAssert<T>> extends AbstractAssert<TankAssert<T>, Tank> {

    public TankAssert(Tank actual) {
        super(actual, TankAssert.class);
    }

    public T hasTankIdentifier(String identifier) {
        isNotNull();
        assertThat(actual.getIdentifier()).isEqualTo(identifier);
        return (T) this;
    }

    public T hasName(String name) {
        isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        return (T) this;
    }

    public T hasLevel(BigDecimal level) {
        isNotNull();
        assertThat(actual.getCurrentLevel()).isEqualTo(level);
        return (T) this;
    }

    public T hasLongitude(BigDecimal longitude) {
        isNotNull();
        assertThat(actual.getLongitude()).isEqualTo(longitude);
        return (T) this;
    }

    public T hasLatitude(BigDecimal latitude) {
        isNotNull();
        assertThat(actual.getLatitude()).isEqualTo(latitude);
        return (T) this;
    }

    public T hasSize(BigDecimal size) {
        isNotNull();
        assertThat(actual.getSize()).isEqualTo(size);
        return (T) this;
    }
}
