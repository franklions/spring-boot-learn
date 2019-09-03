package rabbitmq.client.demo.delayedmessage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列消费端，消费过期消息
 *
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
public class DeadLetterConsumer {

    private static final String dead_letter_exchange = "dead-letter-exchange";
    private static final String deal_letter_routing_key = "deal-letter-routing-key";
    private static final String dead_letter_queue = "dead-letter-queue";

    public static void main(String[] args) {
        System.out.println("********* Deal letter consumer *********");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        try {
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.exchangeDeclare(dead_letter_exchange,"direct");

            channel.queueDeclare(dead_letter_queue, true, false, false, null);
            channel.queueBind(dead_letter_queue, dead_letter_exchange, deal_letter_routing_key);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");

                System.out.println(" [x] Received deal message :'" + message + "'");
                try {
                    System.out.println(" [x] Do work");
                } finally {
                    System.out.println(" [x] Done");
                }
            };

            boolean autoAck = true; // acknowledgment is covered below
            channel.basicConsume(dead_letter_queue, autoAck, deliverCallback, consumerTag -> {
            });
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
