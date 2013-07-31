package com.jensen.nivell.assertions;

import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.AlertRepository;
import org.fest.assertions.api.AbstractAssert;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class AlertRepositoryAssert <T extends AlertRepositoryAssert> extends AbstractAssert<AlertRepositoryAssert<T>, AlertRepository> {

    public AlertRepositoryAssert(AlertRepository actual) {
        super(actual, AlertRepositoryAssert.class);
    }

    public T addMethodIsCalledWith(Alert alert) {
        isNotNull();
        Mockito.verify(actual).add(alert);
        return (T) this;
    }
}


