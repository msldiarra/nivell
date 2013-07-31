package com.jensen.nivell.resources;


import com.google.gson.Gson;
import com.jensen.nivell.models.Tank;
import com.jensen.nivell.models.TankRepository;
import org.junit.Test;

import java.math.BigDecimal;

import static com.jensen.nivell.assertions.ServiceAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TankResourceTest {

    private Gson gson = new Gson();

    @Test
    public void posting_new_alert_uri_calls_repository_set_method() throws Exception {

        BigDecimal size = new BigDecimal("5055.00");
        BigDecimal level = new BigDecimal("5.00");

        BigDecimal latitude = new BigDecimal("-89.23");
        BigDecimal longitude = new BigDecimal("-179.89");

        Tank expectedTank = new Tank(
                "12349875",
                "Sokorodji1",
                size, level,
                latitude, longitude);

        TankRepository tankRepository = mock(TankRepository.class);

        when(tankRepository.get(expectedTank.getPersistenceKey())).thenReturn(gson.toJson(expectedTank));

        ITankResource tankResource = new TankResource(tankRepository);
        tankResource.add(expectedTank.getPersistenceKey(), "Sokorodji1", size, level, latitude, longitude);

        assertThat(tankRepository).addMethodIsCalledWith(expectedTank);
    }

}
