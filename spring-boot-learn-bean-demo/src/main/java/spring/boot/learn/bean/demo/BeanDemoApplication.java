package spring.boot.learn.bean.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import spring.boot.learn.bean.demo.component.TestBean2;
import spring.boot.learn.bean.demo.service.CatchService;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/4/21
 * @since Jdk 1.8
 */
@SpringBootApplication
public class BeanDemoApplication implements CommandLineRunner {
    @Autowired
    CatchService catchService4;

    @Autowired
    CatchService catchService3;

    @Autowired
    TestBean2 testBean2;

    public static void main(String[] args) {
        ApplicationContext ctx =  SpringApplication.run(BeanDemoApplication.class,args);
        CatchService catchService = ctx.getBean(CatchService.class);
        catchService.showName();
        CatchService catchService2 =  ctx.getBean("catchService",CatchService.class);
        catchService2.showName();
//        catchService.setCatch();
        System.out.println("====================分割线==================");
        System.out.println(">>>>>>>>>>>>>>>>>run finish>>>>>>>>>>>>");

    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean(CatchService.class)
    public CatchService catchService(RedisTemplate redisTemplate){
        return new CatchService(redisTemplate);
    }

    @Override
    public void run(String... strings) throws Exception {

        catchService4.showName();
        catchService3.showName();
        System.out.println("====================分割线==================");
        testBean2.print();

    }
}
