package com.xr.consumer.controller;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Configuration
@RequestMapping("con")
public class ConsumController {

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(value = "/get")
    @ResponseBody
    public String getConsumerList(@RequestHeader("Authorization") String authHeader,
                                  @RequestHeader HttpHeaders headers){
        headers.entrySet().stream().forEach(et->{
            System.out.println(et.getKey()+"---"+et.getValue());
        });
        if(authHeader!=null && authHeader.equals("123")){
            return "success";
        }else{
            return "failed!!!!";
        }
    }

    @RequestMapping(value = "/router", method = RequestMethod.GET)
    @ResponseBody
    public String router() {
        // 查找服务列表
        List<ServiceInstance> ins = getServiceInstances();
        // 输出服务信息及状态
        for (ServiceInstance service : ins) {
            EurekaServiceInstance esi = (EurekaServiceInstance) service;
            InstanceInfo info = esi.getInstanceInfo();
            System.out.println(info.getAppName() + "---" + info.getInstanceId()
                    + "---" + info.getStatus());
        }
        return "";
    }

    private List<ServiceInstance> getServiceInstances() {
        List<String> ids = discoveryClient.getServices();
        List<ServiceInstance> result = new ArrayList<ServiceInstance>();
        for (String id : ids) {
            List<ServiceInstance> ins = discoveryClient.getInstances(id);
            result.addAll(ins);
        }
        return result;
    }
}
