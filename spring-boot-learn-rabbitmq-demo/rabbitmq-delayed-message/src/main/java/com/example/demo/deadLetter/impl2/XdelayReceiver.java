package com.example.demo.deadLetter.impl2;

import com.example.demo.deadLetter.entity.Booking;
import com.example.demo.deadLetter.impl1.Constants;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
@Component
@EnableRabbit
@Configuration
public class XdelayReceiver {

    @RabbitListener(queues = Constants.IMMEDIATE_QUEUE_XDELAY)
    public void get(Booking booking) {
        System.out.println("Receive" + booking);
    }
}

