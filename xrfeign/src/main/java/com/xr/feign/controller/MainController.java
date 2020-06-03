package com.xr.feign.controller;

import com.xr.feign.interfaces.ConsumerFeignClient;
import com.xr.feign.interfaces.TestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    TestInterface testInterface;

    @Autowired
    ConsumerFeignClient consumerFeignClient;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) throws InterruptedException {
        System.out.println("request hihi!!!!");
        return testInterface.sayHiFromClientOne(name);
    }

    @GetMapping(value = "/con/get")
    public String getConsumers() {
        return consumerFeignClient.getConsumersList();
    }
}
