package com.jensen.nivell.assertions;

import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.AlertRepository;
import com.jensen.nivell.models.Tank;
import com.jensen.nivell.models.TankRepository;
import org.fest.assertions.api.AbstractAssert;
import org.mockito.Mockito;

public class TankRepositoryAssert<T extends TankRepositoryAssert> extends AbstractAssert<TankRepositoryAssert<T>, TankRepository> {

    public TankRepositoryAssert(TankRepository actual) {
        super(actual, TankRepositoryAssert.class);
    }

    public T addMethodIsCalledWith(Tank tank) {
        isNotNull();
        Mockito.verify(actual).add(tank);
        return (T) this;
    }
}
