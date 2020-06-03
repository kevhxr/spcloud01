package com.xr.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

//@Component
public class UgroupRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        System.out.println("apply UserGroup");
        requestTemplate.header("UserGroup", "AA");
    }
}
