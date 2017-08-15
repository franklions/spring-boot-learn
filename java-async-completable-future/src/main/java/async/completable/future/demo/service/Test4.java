package async.completable.future.demo.service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Caozheng on 2017/6/9.
 */
public class Test4 {
    public static void test(){
        test3();
    }

    private static void test1(){
        getName().thenAccept(System.out::println);
    }

    private static void test2(){
        CompletableFuture<String> f = getName();
        f.thenAccept(System.out::println);
        f.exceptionally(e-> {
            System.out.println(e); //e.getCause()
            return null;
        });

    }

    private static void test3(){
        getName().handleAsync((name,ex)->{
            if(ex != null){
                System.out.println(ex);
                return null;
            }
            else{
                System.out.println(name);
                return name;
            }
        });
    }

    private static CompletableFuture<String> getName(){
        return CompletableFuture.supplyAsync(()->{
            throw new RuntimeException("abc");
        });
    }
}
