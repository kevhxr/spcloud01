package com.xr.feign.controller;

import com.xr.feign.interfaces.TestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    TestInterface testInterface;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return testInterface.sayHiFromClientOne(name);
    }
}
