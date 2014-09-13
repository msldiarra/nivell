package com.jensen.nivell.models;

import com.jensen.nivell.assertions.ConstraintViolationAssert;
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

public class TankTest {

    private Validator validator;

    @Before
    public void setUps(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void clean(){}

    @Test
    public void latitude_and_longitude_are_invalid() {

        BigDecimal latitude = new BigDecimal("-109.23");
        BigDecimal longitude = new BigDecimal("-195.89");
        BigDecimal size = new BigDecimal("-200.00");
        BigDecimal currentLevel = new BigDecimal("8.00");
        Tank tank = new Tank("identifier","name", size, currentLevel, latitude, longitude);

        Set<ConstraintViolation<Tank>> constraintViolations = validator.validate(tank);

        com.jensen.nivell.assertions.RepositoryAssertions.assertThat(tank).hastankReference("identifier");
        com.jensen.nivell.assertions.RepositoryAssertions.assertThat(tank).hasName("name");
        com.jensen.nivell.assertions.RepositoryAssertions.assertThat(tank).hasSize(size);
        com.jensen.nivell.assertions.RepositoryAssertions.assertThat(tank).hasLevel(currentLevel);
        com.jensen.nivell.assertions.RepositoryAssertions.assertThat(tank).hasLatitude(latitude);
        com.jensen.nivell.assertions.RepositoryAssertions.assertThat(tank).hasLongitude(longitude);
        assertThat(constraintViolations).hasSize(3);
        assertThat(constraintViolations)
                .hasErrorMessage("Latitude must be greater than -90.00")
                .hasErrorMessage("Longitude must be greater than -180.00")
                .hasErrorMessage("Size must be a positive value");
    }

    private ConstraintViolationAssert assertThat(Set<ConstraintViolation<Tank>> constraintViolations){
        return new ConstraintViolationAssert(constraintViolations);
    }
}
