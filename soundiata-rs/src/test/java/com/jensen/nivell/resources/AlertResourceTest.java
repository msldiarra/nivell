package com.jensen.nivell.resources;

import com.google.gson.Gson;
import com.jensen.nivell.models.Alert;
import com.jensen.nivell.models.AlertRepository;
import com.jensen.nivell.models.Tank;
import com.jensen.nivell.models.TankRepository;
import org.junit.Test;

import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import java.math.BigDecimal;

import static com.jensen.nivell.assertions.ServiceAssertions.assertThat;
import static org.mockito.Mockito.*;

public class AlertResourceTest {

    private Gson gson = new Gson();

    @Test
    public void posting_new_alert_uri_calls_repository_set_method() throws Exception {

        String time = "2013-10-10 10:10:10";
        BigDecimal level = new BigDecimal("5.00");

        Alert expectedAlert = new Alert("tankReference", level, time);
        Tank expectedTank = new Tank("tankReference", "Sokorodji1",
                null, new BigDecimal("8.00"),new BigDecimal("-89.23"), new BigDecimal("-179.89"));

        AlertRepository alertRepository = mock(AlertRepository.class);
        TankRepository tankRepository = mock(TankRepository.class);

        when(tankRepository.get("tankReference")).thenReturn(gson.toJson(expectedTank));

        IAlertResource alertResource = new AlertResource(alertRepository, tankRepository);
        alertResource.add("tankReference", level, time);

        assertThat(alertRepository).addMethodIsCalledWith(expectedAlert);
    }

    @Test(expected = WebApplicationException.class)
    public void posting_on_new_alert_uri_fails_when_date_wrong_format() throws WebApplicationException {

        String time = "2013-10-10 10:10:10";
        BigDecimal level = new BigDecimal("-5.00");
        Alert expectedAlert = new Alert(null, level, time);

        AlertRepository repository = mock(AlertRepository.class);
        TankRepository tankRepository = mock(TankRepository.class);

        doThrow(new ValidationException()).when(repository).add(expectedAlert);
        IAlertResource alertResource = new AlertResource(repository, tankRepository);
        alertResource.add(null, level, time);

        assertThat(repository).addMethodIsCalledWith(expectedAlert);
    }
}
