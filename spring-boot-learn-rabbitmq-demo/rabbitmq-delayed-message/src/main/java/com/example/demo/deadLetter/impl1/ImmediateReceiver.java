package com.example.demo.deadLetter.impl1;

import com.example.demo.deadLetter.entity.Booking;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
@Component
@EnableRabbit
@Configuration
public class ImmediateReceiver {

    @RabbitListener(queues = Constants.IMMEDIATE_QUEUE)
    @RabbitHandler
    public void get(Booking booking) {
        System.out.println("收到延时消息了" + booking);
    }
}
