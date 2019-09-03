package rabbitmq.client.demo.delayedmessage;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 设置消息过期时间
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
public class PreMessageTTLPublish {

    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final String dead_letter_exchange = "dead-letter-exchange";
    private static final String deal_letter_routing_key = "deal-letter-routing-key";
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //声明一个队列，当myqueue队列中有死信产生时，会转发到交换器some.exchange.name
            Map<String, Object> arguments = new HashMap<String, Object>();
            arguments.put("x-dead-letter-exchange", dead_letter_exchange);

            //如果设置死信会以路由键some-routing-key转发到some.exchange.name，如果没设默认为消息发送到本队列时用的routing key
            arguments.put("x-dead-letter-routing-key", deal_letter_routing_key);
            //为队列消息设置过期时间，所有消息都有效
            arguments.put("x-message-ttl", 1800000);
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, arguments);

            for (int i = 1; i <= 10; i++) {
                String message =  " task"+i ;

//                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//                        .expiration("60000")
//                        .build();

                channel.basicPublish("",TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN.builder().expiration("10000").build(),
                        message.getBytes("UTF-8"));

                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
