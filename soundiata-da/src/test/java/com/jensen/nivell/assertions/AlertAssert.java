package com.jensen.nivell.assertions;

import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.Tank;
import org.fest.assertions.api.AbstractAssert;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

public class AlertAssert<T extends AlertAssert<T>> extends AbstractAssert<AlertAssert<T>, Alert> {

    public AlertAssert(Alert actual) {
        super(actual, AlertAssert.class);
    }

    public T hasRefrenceToTank(String tankReference) {
        isNotNull();
        assertThat(actual.getTankReference()).isEqualTo(tankReference);
        return (T) this;
    }

    public T hasLevel(BigDecimal level) {
        isNotNull();
        assertThat(actual.getLevel()).isEqualTo(level);
        return (T) this;
    }

    public T hasPersistenceKey(String identifier) {
        isNotNull();
        assertThat(actual.getLookupKey()).isEqualTo("tank::" + identifier);
        return (T) this;
    }

    public T belongsTo(Tank tank){

        /* adding a tank is done this way (aka lookup key)
        add("key", tank);
        add(tank.getName(), "key");
        tank = client.get("key");*/

        isNotNull();
        assertThat(actual.getTankReference()).isEqualTo(tank.getReference());
        return null;
    }

    public void hasCompoundUid(String uid) {
        isNotNull();
        assertThat(actual.getUid()).as("alert uid").isEqualTo(uid);
    }
}
