package com.xr.feign.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "xrconsumer")
public interface ConsumerFeignClient {


    @RequestMapping(value = "/con/get")
    String getConsumersList();
}
