package rabbitmq.client.demo.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 发送者确认消息
 * @author administrator
 * @version 1.0
 * @date 2019-08-31
 * @since Jdk 1.8
 */
public class PublishConfirmSend {

    private static final String TASK_QUEUE_NAME = "task_queue";

    private static final TreeSet<Long> tags = new TreeSet<>();

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.107.222.78");
        factory.setPort(31002);
        factory.setUsername("rabbituser");
        factory.setPassword("lwkj0308");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //是当前的channel处于确认模式
            channel.confirmSelect();

            //使当前的channel处于事务模式，与上面的使channel处于确认模式使互斥的
            //channel.txSelect();

            /**
             * deliveryTag 消息id
             * multiple 是否批量
             *      如果是true，就意味着，小于等于deliveryTag的消息都处理成功了
             *      如果是false，只是成功了deliveryTag这一条消息
             */
            channel.addConfirmListener(new ConfirmListener() {
                //消息发送成功并且在broker落地，deliveryTag是唯一标志符，在channek上发布的消息的deliveryTag都会比之前加1
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("=========deliveryTag==========");
                    System.out.println("deliveryTag: "+deliveryTag);
                    System.out.println("multiple: "+multiple);
                    //处理成功发送的消息
                    if(multiple){
                        //批量操作
                        for(Long _id:new TreeSet<>(tags.headSet(deliveryTag+1))){
                            tags.remove(_id);
                        }
                    }else{
                        //单个确认
                        tags.remove(deliveryTag);
                    }

                    System.out.println("未处理的消息: "+tags);
                }

                /**
                 * deliveryTag 消息id
                 * multiple 是否批量
                 *      如果是true，就意味着，小于等于deliveryTag的消息都处理失败了
                 *      如果是false，只是失败了deliveryTag这一条消息
                 */
                //消息发送失败或者落地失败
                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("===========handleNack===========");
                    System.out.println("deliveryTag: "+deliveryTag);
                    System.out.println("multiple: "+multiple);
                }
            });

            /**创建持久化队列 durable =true **/
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            for (int i = 1; i <= 10; i++) {
                String message =  " task"+i ;

                //设置发送消息为持久化消息 MessageProperties.PERSISTENT_TEXT_PLAIN

                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
                tags.add(Long.valueOf(i));
                channel.waitForConfirms();
                //channel.waitForConfirms():表示等待已经发送给broker的消息act或者nack之后才会继续执行。
               // channel.waitForConfirmsOrDie():表示等待已经发送给broker的消息act或者nack之后才会继续执行，如果有任何一个消息触发了nack则抛出IOException。


            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
