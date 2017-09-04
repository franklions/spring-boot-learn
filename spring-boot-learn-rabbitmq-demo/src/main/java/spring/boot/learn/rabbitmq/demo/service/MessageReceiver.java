package spring.boot.learn.rabbitmq.demo.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/25
 * @since Jdk 1.8
 */
@Component
public class MessageReceiver extends MessageListenerAdapter {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("receiver:"+message.getBody());
    }
}
