package com.jensen.nivell.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static com.jensen.nivell.assertions.RepositoryAssertions.assertThat;

public class AlertTest {

    private Validator validator;

    @Before
    public void setUps(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void clean(){}

    @Test
    public void level_is_invalid() {

        Alert alert = new Alert("tankIdentifier", new BigDecimal("-2.7889"), "2013-10-10 10:10:10");

        Set<ConstraintViolation<Alert>> constraintViolations = validator.validate(alert);

        assertThat(alert).hasTankIdentifier("tankIdentifier");
        assertThat(alert).hasLevel(new BigDecimal("-2.7889"));
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations).hasErrorMessage("Liquid level must be a positive value");
    }

    @Test
    public void alert_properties_can_not_be_null() {

        Alert alert = new Alert(null, null, null);

        Set<ConstraintViolation<Alert>> constraintViolations = validator.validate(alert);

        assertThat(alert).hasTankIdentifier(null);
        assertThat(constraintViolations).hasSize(3);
    }
}
