package java.async.completable.future.demo.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.*;

/**
 * Created by Caozheng on 2017/6/9.
 */
public class Test1 {
    public static void test() throws Exception {

        t1(() -> System.out.println("t1-1"));
        t1(() -> {
            System.out.println("t1-2");
        });
        t1(Test1::say);
        t2(p -> System.out.println(p));
        t2(System.out::println);
        final int i = 3;
        t3(() -> "t" + i);
        t4(p -> p + "abc");
    }

    private static void say(){
        System.out.println("t1-3");
    }

    private static void t1(Runnable run) throws Exception{
        Thread.sleep(1000);
        run.run();
    }

    private static void t2(Consumer<String> consumer){ // BiConsumer
        if (true){
            consumer.accept("t2-true");
        }
        else{
            consumer.accept("t2-false");
        }
    }

    private static void t3(Supplier<String> supplier){
        System.out.println(supplier.get());
    }

    private static void t4(Function<String, String> function){ // BiFunction


        String s = function.apply("t4");
        System.out.println(s);
    }
}
