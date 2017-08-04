package spring.boot.learn.rabbitmq.demo.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/25
 * @since Jdk 1.8
 */
@Component
public class MessageReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(message.getBody());
    }
}
