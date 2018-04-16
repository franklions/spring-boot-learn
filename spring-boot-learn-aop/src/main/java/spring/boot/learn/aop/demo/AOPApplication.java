package spring.boot.learn.aop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring.boot.learn.aop.demo.aspect.SpringAspectDemo;
import spring.boot.learn.aop.demo.service.AdviceService;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/16
 * @since Jdk 1.8
 */
@SpringBootApplication
public class AOPApplication  implements CommandLineRunner{

    @Autowired
    AdviceService adviceService;

    public static void main(String[] args) {
        SpringApplication.run(AOPApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("running......");
        adviceService.manyAdvices("test","runtest");
    }
}
