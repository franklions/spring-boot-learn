package rabbitmq.client.demo.delayedmessage;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-02
 * @since Jdk 1.8
 */
public class DelayedPublish {

    private static final String TASK_QUEUE_NAME = "task_delayed_queue";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            Map<String, Object> arguments = new HashMap<String, Object>();
            arguments.put("x-delayed-type", "direct");

            channel.exchangeDeclare("delayed-exchange",
                    "x-delayed-message",true,false,arguments);

            //创建持久化队列 durable =true
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            channel.queueBind(TASK_QUEUE_NAME,"delayed-exchange","delayed-routingKey");

            for (int i = 1; i <= 10; i++) {
                String message =  " task"+i ;

                //设置发送消息的头 MessageProperties.PERSISTENT_TEXT_PLAIN

//
//                Map<String, Object> headers = new HashMap<String, Object>();
//                headers.put("x-delay", 5000);
//                AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers).deliveryMode(MessageProperties.PERSISTENT_TEXT_PLAIN.getDeliveryMode());
//                channel.basicPublish("my-exchange", "", props.build(), messageBodyBytes);
//
//                byte[] messageBodyBytes2 = "more delayed payload".getBytes("UTF-8");
//                Map<String, Object> headers2 = new HashMap<String, Object>();
//                headers2.put("x-delay", 1000);
//                AMQP.BasicProperties.Builder props2 = new AMQP.BasicProperties.Builder().headers(headers2);
//                channel.basicPublish("my-exchange", "", props2.build(), messageBodyBytes2);

                Map<String, Object> headers = new HashMap<String, Object>();
                headers.put("x-delay", 10000);
                channel.basicPublish("delayed-exchange", "delayed-routingKey",
                        MessageProperties.PERSISTENT_TEXT_PLAIN.builder().headers(headers).build(),
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
