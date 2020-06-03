package com.xr.ribbon.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.util.Assert;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.Map;
import java.util.concurrent.*;

public class HystrixTestMain {

    public static void main(String[] args) throws InterruptedException {

/*        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(() -> {
            System.out.println("ss");
        });*/
        testFallbackNest();
        //testCache();
        //testSimpleCommand();

    }

    public static void testSimpleCommand() {
        //每个Command对象只能调用一次,不可以重复调用,
        //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance.
        XrCommand xrCommand = new XrCommand("Syn-hystrix-xr");
        //使用execute()同步调用代码,效果等同于:xrCommand.queue().get();
        System.out.println();
        System.out.println();
        String result = xrCommand.execute();
        System.out.println("result=" + result);

        xrCommand = new XrCommand("Asyn-hystrix-xr");
        //异步调用,可自由控制获取结果时机,
        Future<String> future = xrCommand.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        try {
            result = future.get(3000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("result=" + result);
        System.out.println("mainThread=" + Thread.currentThread().getName());
    }

    public static void testAysncCallback() {
        XrCommand xrCommand = new XrCommand("World");
        Observable<String> fs = xrCommand.observe();
        fs.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                // onNext/onError完成之后最后回调
                System.out.println("execute onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                // 当产生异常时回调
                System.out.println("onError " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                // 获取结果后回调
                System.out.println("onNext: " + v);
            }
        });
        fs.subscribe(new Action1<String>() {
            @Override
            public void call(String result) {
                //执行结果处理,result 为HelloWorldCommand返回的结果
                //用户对结果做二次处理.
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("act1: " + result);
            }
        });

    }


    public static void testCache(){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            CachedCommand command2a = new CachedCommand("11");
            CachedCommand command2b = new CachedCommand("11");
            System.out.println(command2a.execute());
            //isResponseFromCache判定是否是在缓存中获取结果
            System.out.println(command2a.isResponseFromCache());
            System.out.println(command2b.execute());
            System.out.println(command2b.isResponseFromCache());
            CachedCommand command3b = new CachedCommand("11");
            CachedCommand command4b = new CachedCommand("11");
            System.out.println(command3b.execute());
            System.out.println(command3b.isResponseFromCache());
            System.out.println(command4b.execute());
            System.out.println(command4b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
        context = HystrixRequestContext.initializeContext();
        try {
        } finally {
            context.shutdown();
        }
    }

    public static void testFallbackNest(){
        for (int i = 0; i <20 ; i++) {
            try {
                System.out.println("===========" +  new FallbackViaNetworkCommand(i).execute());
            }catch(Exception e) {
                System.out.println("run()抛出HystrixBadRequestException时，被捕获到这里" + e.getCause());
            }
        }
        System.out.println("------开始打印现有线程---------");
        Map<Thread, StackTraceElement[]> map=Thread.getAllStackTraces();
        for (Thread thread : map.keySet()) {
            System.out.println(thread.getName());
        }
        System.out.println("thread num: " + map.size());
    }
}
