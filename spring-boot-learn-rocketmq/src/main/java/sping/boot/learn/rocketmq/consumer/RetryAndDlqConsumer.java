package sping.boot.learn.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 重试队列和死信队列
 * @author flsh
 * @version 1.0
 * @date 2019-04-04
 * @since Jdk 1.8
 */
public class RetryAndDlqConsumer {
    public static void main(String[] args) {
        try {
            //声明并初始化一个consumer
            //需要一个consumer group名字作为构造方法的参数，这里为concurrent_consumer
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("retry_consumer");

            //同样也要设置NameServer地址
            consumer.setNamesrvAddr("47.94.212.70:9876");

            //这里设置的是一个consumer的消费策略
            //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
            //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
            //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            //设置重试多少次投入到死信队列
            consumer.setMaxReconsumeTimes(3);

            //设置consumer所订阅的Topic和Tag
            consumer.subscribe("TopicTest", "TagA || TagB || TagC || TagD || TagE");

            //设置一个Listener，主要进行消息的逻辑处理
            //注意这里使用的是MessageListenerOrderly这个接口
            consumer.registerMessageListener(new MessageListenerConcurrently() {

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {

                    for (MessageExt msg:msgs){
                        //获取当前重试的次数；
                        int retry =  msg.getReconsumeTimes();
                        System.out.println("retry :"+retry);
                    }

                    System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);

                    //返回消费状态
                    //CONSUME_SUCCESS 消费成功
                    //RECONSUME_LATER 消费失败，需要稍后重新消费
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            });

            //调用start()方法启动consumer
            consumer.start();

            System.out.println("Consumer Started.");
        }catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
