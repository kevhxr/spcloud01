package com.xr.feign.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    Retryer feignRetryer() {
        return  new Retryer.Default();
    }
}
