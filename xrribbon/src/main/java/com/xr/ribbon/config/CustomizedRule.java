package com.xr.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizedRule {
    @Bean
    public IRule myRule()
    {
        return new XrRule();//Ribbon默认是轮询，我自定义为随机
    }
}
