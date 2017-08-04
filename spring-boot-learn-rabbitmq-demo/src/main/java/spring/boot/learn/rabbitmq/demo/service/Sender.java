package spring.boot.learn.rabbitmq.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/25
 * @since Jdk 1.8
 */
@Component
public class Sender {
    @Resource(name="firstRabbitTemplate")
    private RabbitTemplate firstRabbitTemplate;

    @Resource(name="secondRabbitTemplate")
    private RabbitTemplate secondRabbitTemplate;

    public void send1() {
        String context = "hello1 " + new Date();
        System.out.println("Sender : " + context);
        this.firstRabbitTemplate.convertAndSend("hello1", context);
    }

    public void send2() {
        String context = "hello2 " + new Date();
        System.out.println("Sender : " + context);
        this.secondRabbitTemplate.convertAndSend("hello2", context);
    }
}
