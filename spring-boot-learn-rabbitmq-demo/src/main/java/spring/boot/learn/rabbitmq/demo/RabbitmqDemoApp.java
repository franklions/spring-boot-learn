package spring.boot.learn.rabbitmq.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.boot.learn.rabbitmq.demo.service.Sender;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/24
 * @since Jdk 1.8
 */
@SpringBootApplication
public class RabbitmqDemoApp implements CommandLineRunner {

    @Autowired
    Sender sender;
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApp.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        while (true) {

            sender.send1();
            sender.send2();
            Thread.sleep(10000);
        }
//        System.out.println(">>>>>>>>>>>>>>>发送完成");
    }
}
