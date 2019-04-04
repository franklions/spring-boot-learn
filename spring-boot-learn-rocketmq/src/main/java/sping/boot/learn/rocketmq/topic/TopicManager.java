package sping.boot.learn.rocketmq.topic;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-04
 * @since Jdk 1.8
 */
public class TopicManager {
    public static void main(String[] args) {
//        onProduceCreateTopic();

    }

    private static void onProduceCreateTopic() {
        DefaultMQProducer orderedProducer = new DefaultMQProducer("topic_producer");

        orderedProducer.setNamesrvAddr("39.107.65.249:9876;47.94.21.223:9876");
        try {
            orderedProducer.start();
            /**
             * key 为集群中已经存在的topic名称，根据已经存在的topic查找broker信息，在这些broker上创建新的topic
             */
            orderedProducer.createTopic("DefaultCluster","NewOnlyQueueTopic",1);

            for(int i=0;i<10;i++) {
                orderedProducer.send(new Message("NewOnlyQueueTopic",
                        ("Hello RocketMQ " +i).getBytes(RemotingHelper.DEFAULT_CHARSET)));
                System.out.println("rocket:"+i);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

        orderedProducer.shutdown();

    }


}
