package java.async.completable.future.demo.service;

import java.util.concurrent.CompletableFuture;
/**
 * Created by Caozheng on 2017/6/9.
 */
public class Test3 {

    public static void test() throws Exception{
        test1();
    }

    private static void test1() throws Exception{
        CompletableFuture<Integer> f_bob =
                getIdFromRedis("bob").thenCompose(Test3::getAgeFromDB);
        CompletableFuture<Integer> f_josh =
                getIdFromRedis("josh").thenCompose(Test3::getAgeFromDB);
        CompletableFuture<Integer> f_ben =
                getIdFromRedis("ben").thenCompose(Test3::getAgeFromDB);


        CompletableFuture.allOf(f_bob, f_josh, f_ben);

        int maxAge = Math.max(Math.max(f_bob.get(), f_josh.get()), f_ben.get());

        System.out.println(maxAge);
    }

    private static void test2() throws Exception{
        CompletableFuture<Integer> f_bob =
                getIdFromRedis("bob").thenCompose(Test3::getAgeFromDB);
        CompletableFuture<Integer> f_josh =
                getIdFromRedis("josh").thenCompose(Test3::getAgeFromDB);
        CompletableFuture<Integer> f_ben =
                getIdFromRedis("ben").thenCompose(Test3::getAgeFromDB);

        CompletableFuture<Integer> mid =
                f_bob.thenCombine(f_josh, (left, right)-> Math.max(left, right));
        f_ben.thenCombine(mid, (left, right)-> Math.max(left, right))
                .thenAccept(System.out::println);
    }

    private static CompletableFuture<String> getIdFromRedis(String name){
        return CompletableFuture.supplyAsync(()->{
            if (name == "bob"){

                return "1";
            }
            else if (name == "josh"){
                return "2";
            }
            else{
                return "3";
            }
        });
    }

    private static CompletableFuture<Integer> getAgeFromDB(String id){
        return CompletableFuture.supplyAsync(()->{
            if (id == "1"){

                return 21;
            }
            else if (id == "2"){
                return 22;
            }
            else{
                return 23;
            }
        });
    }
}
