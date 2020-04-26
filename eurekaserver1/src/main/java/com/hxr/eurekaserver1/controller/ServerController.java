package com.hxr.eurekaserver1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class ServerController {

    @RequestMapping("/test")
    public String test(){
        return "test123";
    }
}
