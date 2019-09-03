package com.example.demo.deadLetter.impl2;

import com.example.demo.deadLetter.entity.Booking;
import com.example.demo.deadLetter.impl1.Constants;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
@Service
public class XdelaySender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Booking booking, int delayTime) {
        System.out.println("delayTime" + delayTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.rabbitTemplate.convertAndSend(Constants.DELAYED_EXCHANGE_XDELAY, Constants.DELAY_ROUTING_KEY_XDELAY, booking,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setDelay(delayTime);
                        System.out.println(sdf.format(new Date()) + " Delay sent.");
                        return message;
                    }
        });
    }
}
