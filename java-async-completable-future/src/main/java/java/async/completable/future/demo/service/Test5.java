package java.async.completable.future.demo.service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Caozheng on 2017/6/9.
 */
public class Test5 {

    public static void test() throws Exception{
        test2();
    }

    private static void test1() throws Exception{
        getNameFromDB(name->{
            System.out.println(name);
            getIdWithName(name, id->{
                System.out.println(id);
            });
        });
    }

    private static void test2() throws Exception{
        CompletableFuture<String> f = getNameFromDBFuture()
                .thenCompose(Test5::getIdWithNameFuture);

        System.out.println(f.get());
    }

    private static void getNameFromDB(MyCallback callback){
        callback.callback("abc");
    }

    private static void getIdWithName(String name, MyCallback callback){
        callback.callback("1");
    }

    private static CompletableFuture<String> getNameFromDBFuture(){
        CompletableFuture<String> future = new CompletableFuture<>();

        getNameFromDB(name->{
            future.complete(name);
        });

        return future;
    }

    private static CompletableFuture<String> getIdWithNameFuture(String name){
        CompletableFuture<String> future = new CompletableFuture<>();

        getIdWithName(name, id->{
            future.complete(id);
        });

        return future;
    }

    private static interface MyCallback{
        public void callback(String str);
    }
}
