package com.jensen.nivell.assertions;

import com.jensen.nivell.models.Alert;
import org.fest.assertions.api.AbstractAssert;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

public class AlertAssert<T extends AlertAssert<T>> extends AbstractAssert<AlertAssert<T>, Alert> {

    public AlertAssert(Alert actual) {
        super(actual, AlertAssert.class);
    }

    public T hasTankIdentifier(String tankIdentifier) {
        isNotNull();
        assertThat(actual.getTankIdentifier()).isEqualTo(tankIdentifier);
        return (T) this;
    }

    public T hasLevel(BigDecimal level) {
        isNotNull();
        assertThat(actual.getLevel()).isEqualTo(level);
        return (T) this;
    }
}
