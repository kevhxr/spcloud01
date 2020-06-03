package com.xr.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HeartBeatService {
    @Autowired
    RestTemplate restTemplate;

/*    @HystrixCommand(fallbackMethod = "handleFailure")*/
    public String hbService(String name) {
        return restTemplate.getForObject("http://eureka-service1/stock?name=" + name, String.class);
    }

    public String handleFailure(String name) {
        return "hi," + name + ", sorry but we encounter failure!!!";
    }
}
