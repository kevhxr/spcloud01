package com.xr.xrservice1.service;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.xr.xrservice1.HealthCheckIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckService implements HealthCheckHandler {

    @Autowired
    private HealthCheckIndicator indicator;

    @Override
    public InstanceStatus getStatus(InstanceStatus currentStatus) {
        Status s = indicator.health().getStatus();
        if(s.equals(Status.UP)) {
            System.out.println("正常连接");
            return InstanceStatus.UP;
        } else {
            System.out.println("无法连接");
            return InstanceStatus.DOWN;
        }
    }
}
