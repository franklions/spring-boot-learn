package java.async.completable.future.demo.service;

import com.sun.corba.se.impl.orbutil.closure.Future;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Caozheng on 2017/6/9.
 */
public class Test2 {
    public static void test() throws Exception {
        t5();
    }

    private static void t1() throws Exception {
        CompletableFuture.runAsync(() -> {
            sleep();
            System.out.println("1");
        });
        System.out.println("2");
    }

    private static void t2() throws Exception {
        CompletableFuture<String> f = getT2();
        System.out.println(f.get()); //f.wait();
    }

    private static void t3() throws Exception {
        //getT2().thenAccept(System.out::println);
        getT2().thenAccept(x -> {
            x = x + "123";
            System.out.println(x);
        });
        System.out.println("abcdefg");
    }

    private static void t4() throws Exception {
        CompletableFuture
                .runAsync(() -> {
                    System.out.println("t4");
                })
                .thenAccept(p -> System.out.println(p));
    }

    private static void t5() throws Exception{
        CompletableFuture.runAsync(()->{
            printTID("a-1");
        }).thenAccept(p->{
            printTID("a-1");
        });

        CompletableFuture.runAsync(()->{
            printTID("b-1");
        }).thenAcceptAsync(p->{
            printTID("b-1");
        });

        CompletableFuture.runAsync(()->{
            printTID("a-2");
        }).thenAccept(p->{
            printTID("a-2");
        });

        CompletableFuture.runAsync(()->{
            printTID("b-2");
        }).thenAcceptAsync(p->{
            printTID("b-2");
        });

        CompletableFuture.runAsync(()->{
            printTID("a-3");
        }).thenAccept(p->{
            printTID("a-3");
        });

        CompletableFuture.runAsync(()->{
            printTID("b-3");
        }).thenAcceptAsync(p->{
            printTID("b-3");
        });
    }

    private static CompletableFuture<String> getT2() {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return "t2";
        });
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
    }

    private static void printTID(String name) {
        System.out.println(name + ":" + Thread.currentThread().getId());
    }
}
