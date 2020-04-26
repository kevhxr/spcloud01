package com.xr.feign.service;

import com.xr.feign.interfaces.TestInterface;
import org.springframework.stereotype.Service;

@Service
public class HystricService implements TestInterface {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry we got failure here !!! " + name;
    }
}
