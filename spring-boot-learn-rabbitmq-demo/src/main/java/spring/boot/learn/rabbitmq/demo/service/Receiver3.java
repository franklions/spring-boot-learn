package spring.boot.learn.rabbitmq.demo.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/2
 * @since Jdk 1.8
 */
@Component
public class Receiver3 {

    @RabbitListener(queues = "#{firstQueue}", containerFactory="firstFactory")
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody())+"33333333333333");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        GetRunnable getRunnable = new GetRunnable(callbackService, message, channel, TrayIcon.MessageType.data.ordinal(), System.currentTimeMillis());
//        initPool.execute(getRunnable);
    }

    @RabbitListener(queues = "#{secondQueue}", containerFactory="secondFactory")
    public void onMessage2(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody())+"ssssssssssss");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        GetRunnable getRunnable = new GetRunnable(callbackService, message, channel, TrayIcon.MessageType.data.ordinal(), System.currentTimeMillis());
//        initPool.execute(getRunnable);
    }
}
