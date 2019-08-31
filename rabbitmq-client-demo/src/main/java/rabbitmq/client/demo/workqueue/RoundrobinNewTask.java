package rabbitmq.client.demo.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-08-30
 * @since Jdk 1.8
 */
public class RoundrobinNewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //创建持久化队列 durable =true
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            for (int i = 1; i <= 10; i++) {
                String message =  " task"+i ;

                //设置发送消息的头 MessageProperties.PERSISTENT_TEXT_PLAIN

                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
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
