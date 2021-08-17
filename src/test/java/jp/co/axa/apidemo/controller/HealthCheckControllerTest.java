package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.controllers.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockitoAnnotations;

public class HealthCheckControllerTest {

    @InjectMocks
    HealthCheckController healthCheckController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return heathy status")
    void shouldReturnHealthyStatus(){
        String response = healthCheckController.healthcheck();
        String expected = "healthy";
        Assertions.assertEquals(expected, response);
    }
}
