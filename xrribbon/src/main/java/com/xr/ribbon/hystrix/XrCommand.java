package com.xr.ribbon.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class XrCommand extends HystrixCommand<String> {
    private final String name;

    public XrCommand(String name) {
        //最少配置:指定命令组名(CommandGroup)
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("XrGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                .andCommandPropertiesDefaults
                        (HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds
                                (5000))
                .andCommandPropertiesDefaults
                        (HystrixCommandProperties.Setter().withExecutionIsolationStrategy
                                (HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }


    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }

    @Override
    protected String run() throws Exception {
        int a = 0;

       /* while(a<10){

            Thread.sleep(100);
            System.out.println("bb:"+a);
            a++;
        }*/
        return "Dear " + name + " thread:" + Thread.currentThread().getName();
    }


}
