package com.xr.feign.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class AuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("apply auth");
/*        requestTemplate.header("Authorization", "123");

        if (HttpMethod.GET.toString() == requestTemplate.method()) {
            //feign 不支持GET方法传输POJO 转换成json，再换成query
            System.out.println("sda");
            try {
                Map<String, Collection<String>> map =new HashMap<>();
                List<String> products = new ArrayList<>();
                products.add("apple");
                products.add("sumsang");
                products.add("huawei");
                map.put("cellphone",products);
                requestTemplate.queries(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
