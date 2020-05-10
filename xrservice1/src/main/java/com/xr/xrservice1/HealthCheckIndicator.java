package com.xr.xrservice1;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HealthCheckIndicator implements HealthIndicator {
    @Override
    public Health health() {

        Random random = new Random();
        if (random.nextBoolean()) {
            return new Builder(Status.UP).build();
        } else {
            return new Builder(Status.UP).build();
        }
    }
}
