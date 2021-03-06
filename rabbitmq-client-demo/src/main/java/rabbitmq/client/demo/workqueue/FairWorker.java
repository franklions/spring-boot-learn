package rabbitmq.client.demo.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 平均分配消息 持久化队列 消息手动确认
 * @author administrator
 * @version 1.0
 * @date 2019-08-30
 * @since Jdk 1.8
 */
public class FairWorker {
    private static final String TASK_QUEUE_NAME = "durable_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("********* Fair Worker1 *********");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        /**
         * 设置持久化队列 durable =true
         * */
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /** 设置平均分配 */
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
                /** 手动确认ACK信息 */
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        //设置自动ACK为false
        boolean autoAck = false;
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
