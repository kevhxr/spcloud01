package com.xr.xrservice1;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.util.Random;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class EurekaServiceApp {

    public static int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApp.class, args);
    }

    @Autowired
    RestTemplate restTemplate;
    @Value("${server.port}")
    String port;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "failureAdvice")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        Random random = new Random();
        if (random.nextBoolean()) {
            throw new RuntimeException("ahhhh!!!!");
        }
        if (1 + 1 == 2) {
            throw new RuntimeException("ahhhh!!!!");
        }
        return "hi " + name + " ,i am from port:" + port;
    }

    @RequestMapping("/stock")
    public String getStock(@RequestParam(value = "name", defaultValue = "AMD") String name) throws InterruptedException {

        System.out.println("getStockBasicInfo arrive");
        int a = 1;
        if (count < 1) {
            Thread.sleep(6000);
        } else {
            Thread.sleep(2000);

        }
        count++;
        System.out.println("finish!!" + count);
        return "hi stock: " + name + " , port No is:" + port;
    }


    @RequestMapping("/nba")
    @HystrixCommand(fallbackMethod = "nbaFail",
            observableExecutionMode = ObservableExecutionMode.EAGER//observe方式
            /*,observableExecutionMode = ObservableExecutionMode.LAZY toObservable方式*/
            /*,ignoreExceptions = {Exception.class} 定义忽略异常，则遇到该类异常将不再执行fallback方法，
            直接包装后抛出*/
    )
    public Observable<String> getNbaTeams() {
        return Observable.create(subscriber -> {

            try {
                String result = restTemplate.getForEntity("http://eureka-service3/nba", String.class)
                        .getBody();
                subscriber.onNext("Mr.Lee's ID:");
                int i = 1 / 0; //抛异常，模拟服务降级
                Random random = new Random();
                subscriber.onNext(String.valueOf(random.nextInt(11)));
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @RequestMapping("/nba1")
    @HystrixCommand(fallbackMethod = "nbaFail",
            observableExecutionMode = ObservableExecutionMode.LAZY
    )
    public Observable<String> getNbaTeams1() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext("找到");
                        subscriber.onNext("Mr Lin");
                        //int i = 1/0; ////抛异常，模拟服务降级
                        subscriber.onNext("了");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public String nbaFail() {
        return "hi,sorry,nba 1 failed!";
    }

    public String failureAdvice(String name) {
        return "hi," + name + ",sorry,service 1 failed!";
    }
}
