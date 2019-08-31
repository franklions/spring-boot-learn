package rabbitmq.client.demo.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-08-30
 * @since Jdk 1.8
 */
public class RoundrobinWorker3 {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("********* Worker3 *********");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
            }
        };

        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });

    }

    private static void doWork(String message) {
        System.out.println("Begin working:"+message);
        try {
            if (message.trim().equals("task3")) {
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println("End working:"+message);
    }
}
