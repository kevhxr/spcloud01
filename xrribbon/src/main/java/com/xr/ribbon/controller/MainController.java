package com.xr.ribbon.controller;

import com.xr.ribbon.service.HeartBeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    HeartBeatService heartBeatService;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return heartBeatService.hbService(name);
    }
}
