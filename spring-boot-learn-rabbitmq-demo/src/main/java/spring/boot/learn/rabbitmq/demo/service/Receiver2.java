package spring.boot.learn.rabbitmq.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/9/4
 * @since Jdk 1.8
 */
@Component
@RabbitListener(queues = "hello2", containerFactory="secondFactory" )
public class Receiver2 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }
}
