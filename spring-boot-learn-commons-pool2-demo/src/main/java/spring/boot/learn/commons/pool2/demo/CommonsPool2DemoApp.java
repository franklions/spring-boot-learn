package spring.boot.learn.commons.pool2.demo;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import spring.boot.learn.commons.pool2.demo.service.FakeObject;
import spring.boot.learn.commons.pool2.demo.service.FakeObjectFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.System.out;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
public class CommonsPool2DemoApp {
    private static KeyedObjectPool<String,FakeObject> pool;
    private static GenericKeyedObjectPoolConfig config;

    static{
        config = new GenericKeyedObjectPoolConfig();
        pool = new GenericKeyedObjectPool<String, FakeObject>(new FakeObjectFactory(),config);
    }

    private static GenericKeyedObjectPool<String, FakeObject> getPool(Consumer<GenericKeyedObjectPoolConfig> action){
        GenericKeyedObjectPoolConfig config
                = new GenericKeyedObjectPoolConfig();

        if(action != null){
            action.accept(config);
        }

        GenericKeyedObjectPool<String, FakeObject> pool
                = new GenericKeyedObjectPool<>(new FakeObjectFactory(), config);

        return pool;

    }



    /**
     * 打印所有配置的默认信息
     */
    private static void printDefaultConfig(){
        out.println("MinEvictableIdleTimeMillis : " + config.getMinEvictableIdleTimeMillis());
        out.println("TimeBetweenEvictionRunsMillis : " + config.getTimeBetweenEvictionRunsMillis());
        out.println("TestWhileIdle : " + config.getTestWhileIdle());
        out.println("BlockWhenExhausted : " + config.getBlockWhenExhausted());
        out.println("EvictionPolicyClassName : " + config.getEvictionPolicyClassName());
        out.println("SoftMinEvictableIdleTimeMillis : " + config.getSoftMinEvictableIdleTimeMillis());
        out.println("MaxTotal : " + config.getMaxTotal());
        out.println("MaxTotalPerKey : " + config.getMaxTotalPerKey());
        out.println("MaxIdlePerKey : " + config.getMaxIdlePerKey());
        out.println("MaxWaitMillis : " + config.getMaxWaitMillis());
        out.println("Fairness : " + config.getFairness());
        out.println("Lifo : " + config.getLifo());
        out.println("NumTestsPerEvictionRun : " + config.getNumTestsPerEvictionRun());
        out.println("TestOnBorrow : " + config.getTestOnBorrow());
        out.println("TestOnCreate : " + config.getTestOnCreate());
        out.println("TestOnReturn : " + config.getTestOnReturn());

    }

    private static void blockAndCount(GenericKeyedObjectPool<String, FakeObject> pool){
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            int i = 1;
            while(true) {

                try {
                    //FakeObject object = pool.borrowObject("a");
                    System.out.println(i++ + " active: " + pool.getNumActive("a") + " idle： " + pool.getNumIdle("a"));
                    //pool.returnObject("a", object);
                    Thread.sleep(1000);

                } catch (Exception e) {
                }
            }
        });
        try {
            System.in.read();
        }
        catch (Exception e){

        }
        finally {
            future.cancel(true);
        }
    }

    /**
     * 尝试耗尽池中元素，最终会抛出 java.util.NoSuchElementException
     * 注意如果不设置MaxWaitMillis则会永远等待下去
     */
    private static void exhausted() {
        GenericKeyedObjectPool<String, FakeObject> pool =
                getPool(p -> {
                    p.setMaxTotalPerKey(3);
                    p.setMaxWaitMillis(1000);
                });

        try {
            for (int i = 0; i < 5; i++) {
                FakeObject object = pool.borrowObject("a");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * lifo实际上就是一个栈，这是有道理的
     */
    private static void lifo(){
        GenericKeyedObjectPool<String, FakeObject> pool = getPool(null);

        try {
            FakeObject object1 = pool.borrowObject("a");
            out.println(object1);
            FakeObject object2 = pool.borrowObject("a");
            out.println(object2);
            pool.returnObject("a", object2);
            pool.returnObject("a", object1);

            out.println("============================");

            out.println(pool.borrowObject("a"));
            out.println(pool.borrowObject("a"));
            out.println(pool.borrowObject("a"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 在几个阶段可以测试元素是否合法，并且在不合法的时会被销毁
     */
    private static void validate(){
        GenericKeyedObjectPool<String, FakeObject> pool =
                getPool(p -> {
                    p.setTestOnBorrow(true);
                    p.setTestOnCreate(true);
                    p.setTestOnReturn(true);
                });
        try {
            FakeObject object = pool.borrowObject("a");
            object.setValid(false);
            pool.returnObject("a", object);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 各种关于空闲清除的测试
     */
    private static void evict(){

        class Settings{
            public String title;
            public int initNum;
        }


        BiConsumer<GenericKeyedObjectPool<String, FakeObject>, Settings> action = (p, settings)->{
            try {
                out.println(settings.title);

                for (int i = 0; i < settings.initNum; i++) {
                    p.addObject("a");
                }

                blockAndCount(p);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        };

        GenericKeyedObjectPool<String, FakeObject> pool =
                getPool(p -> {
                    p.setTimeBetweenEvictionRunsMillis(3000);
                    p.setSoftMinEvictableIdleTimeMillis(1000);
                    p.setMinIdlePerKey(1);
                });
        Settings settings = new Settings(){{
            title = "first";
            initNum = 5;
        }};


        /**
         * 不evict所有，只evict3个
         */
        // action.accept(pool, settings);

        pool =
                getPool(p -> {
                    p.setTimeBetweenEvictionRunsMillis(3000);
                    p.setSoftMinEvictableIdleTimeMillis(1000);
                    p.setMinIdlePerKey(1);
                    p.setNumTestsPerEvictionRun(5);
                });
        settings = new Settings(){{
            title = "first";
            initNum = 5;
        }};

        /**
         * evict所有，直到达到MinIdlePerKey
         */
        // action.accept(pool, settings);

        pool =
                getPool(p -> {
                    p.setTimeBetweenEvictionRunsMillis(3000);
                    p.setSoftMinEvictableIdleTimeMillis(1000);
                    p.setMinIdlePerKey(1);
                    p.setNumTestsPerEvictionRun(-1);
                });
        settings = new Settings(){{
            title = "first";
            initNum = 5;
        }};

        /**
         * 实际上 -1 并不是代表全部清除，只是计算结果和全部清除一样
         */
        // action.accept(pool, settings);

        pool =
                getPool(p -> {
                    p.setTimeBetweenEvictionRunsMillis(10000);
                    p.setSoftMinEvictableIdleTimeMillis(1000);
                    p.setMinEvictableIdleTimeMillis(5000);
                    p.setMinIdlePerKey(5);
                    p.setNumTestsPerEvictionRun(-1);
                });

        settings = new Settings(){{
            title = "first";
            initNum = 10;
        }};

        /**
         * 开始想初始化10个元素，但受限于隐含的MaxTotalPerKey/MaxIdlePerKey， 只初始化了8个元素
         * 10秒之后开始evict,soft evict会留下5(MinIdlePerKey个)，但全部满足MinEvictableIdleTimeMillis的条件
         * 所以全部evict
         */
        //action.accept(pool, settings);

        pool =
                getPool(p -> {
                    p.setTimeBetweenEvictionRunsMillis(3000);
                    p.setSoftMinEvictableIdleTimeMillis(1000);
                    p.setMinEvictableIdleTimeMillis(5000);
                    p.setMinIdlePerKey(6);
                    p.setNumTestsPerEvictionRun(-1);
                });

        settings = new Settings(){{
            title = "first";
            initNum = 10;
        }};

        /**
         * 全部清除会导致内部数据结构对应key不存在，原本应该有的补全到MinIdlePerKey的操作不会执行，保
         * 证至少有一个元素不会被清除可以解决这个问题，可以将blockAndCount中注释的代码还原即可看到
         */
        action.accept(pool, settings);
    }


    public static void main(String[] args) throws Exception{
        evict();

    }

}
