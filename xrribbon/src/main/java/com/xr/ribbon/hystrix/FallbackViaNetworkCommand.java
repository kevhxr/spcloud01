package com.xr.ribbon.hystrix;

import com.netflix.hystrix.*;

import java.util.Random;

public class FallbackViaNetworkCommand extends HystrixCommand<String> {

    private final int id;

    protected FallbackViaNetworkCommand(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueCommand"))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter().withCoreSize(20))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerEnabled(true)
                                .withCircuitBreakerRequestVolumeThreshold(3)
                                .withCircuitBreakerErrorThresholdPercentage(50)));
        this.id = id;
    }


    @Override
    protected String run() throws Exception {
        System.out.println("running run():" + id);
        int num = Integer.valueOf(id);
        if(num < 5) {	// 直接返回
            return id+"";
        } else {	// 无限循环模拟超时
            int j = 0;
            while (true) {
                j++;
            }
        }
    }

    @Override
    protected String getFallback() {
        return "CircuitBreaker fallback: " + id;
        //return new FallbackViaNetwork(id).execute();
    }

    private static class FallbackViaNetwork extends HystrixCommand<String> {
        private final int id;

        public FallbackViaNetwork(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueFallbackCommand"))
                    // 使用不同的线程池做隔离，防止上层线程池跑满，影响降级逻辑.
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServiceXFallback")));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
/*            Random random = new Random();
            int a = 0;
            if (a < 1 || random.nextBoolean()) {
                throw new Exception("network break: " + id);
            }*/
            return "network successfully get data back: " + id;
        }

        @Override
        protected String getFallback() {
            return null;
        }
    }
}
