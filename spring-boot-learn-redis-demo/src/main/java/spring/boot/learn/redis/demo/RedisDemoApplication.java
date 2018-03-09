package spring.boot.learn.redis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import spring.boot.learn.redis.demo.service.CatchService;
import spring.boot.learn.redis.demo.service.LuaScriptService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/4/20
 * @since Jdk 1.8
 */
@SpringBootApplication
public class RedisDemoApplication implements CommandLineRunner {

    @Autowired
     CatchService catchService4;

    @Autowired
     CatchService catchService3;

    @Autowired
    LuaScriptService luaScriptService;

    public static void main(String[] args) {
      ApplicationContext ctx =  SpringApplication.run(RedisDemoApplication.class,args);
        CatchService catchService = ctx.getBean(CatchService.class);
        catchService.showName();
        CatchService catchService2 =  ctx.getBean("catchService",CatchService.class);
        catchService2.showName();
        catchService.setCatch();
        System.out.println("====================分割线==================");
        System.out.println(">>>>>>>>>>>>>>>>>run finish>>>>>>>>>>>>");


    }

//    @Bean
//    @Scope("prototype")
//    @ConditionalOnMissingBean(CatchService.class)
//    public CatchService catchService(StringRedisTemplate redisTemplate){
//        return new CatchService(redisTemplate);
//    }

    @Override
    public void run(String... strings) throws Exception {

        catchService4.showName();
        catchService3.showName();
        System.out.println("====================分割线==================");
       Boolean retval =  luaScriptService.checkAndSet("myappid","1231231231");
        System.out.println("return value:\t"+retval);

        Thread check = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> keys = new ArrayList<>();
                keys.clear();
                for (int i =1;i<100000;i++) {
                    keys.add(luaScriptService.getUUID());
                }

                while (true){
                    Long beginTime = System.currentTimeMillis();
                    for(String key :keys){
                        luaScriptService.handleExpiration(key);
                    }
                    System.out.println("执行Lua脚本用时："+(System.currentTimeMillis() - beginTime));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        check.setDaemon(true);
        check.start();

        Thread rate = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> keys = new ArrayList<>();
                keys.clear();
                for (int i =1;i<100000;i++) {
                    keys.add(luaScriptService.getUUID() + "_quota");
                }

                while (true){
                    Long beginTime = System.currentTimeMillis();
                    for(String key :keys){
                        luaScriptService.handleExpirationByCommand(key);
                    }
                    System.out.println("执行Redis指令用时："+(System.currentTimeMillis() - beginTime));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rate.setDaemon(true);
        rate.start();
        System.in.read();
    }
}
