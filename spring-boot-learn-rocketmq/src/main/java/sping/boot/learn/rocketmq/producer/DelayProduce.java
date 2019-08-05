package sping.boot.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-04
 * @since Jdk 1.8
 */
public class DelayProduce {
    public static void main(String[] args) {

            //声明并初始化一个producer
            //需要一个producer group名字作为构造方法的参数，这里为concurrent_producer
            DefaultMQProducer producer = new DefaultMQProducer("delay_producer");

            //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
            //NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码里
            producer.setNamesrvAddr("47.94.212.70:9876");

        try {
            //调用start()方法启动一个producer实例
            producer.start();


            //发送10条消息到Topic为TopicTest，tag为TagA，消息内容为“Hello RocketMQ”拼接上i的值
//            for (int i = 0; i < 10; i++) {
                try {
                    Message msg = new Message("06e91681c05b43b5bd5f1c9dd0f7cb97_orderly",// topic
                            "",// tag
                            ("06e91681c05b43b5bd5f1c9dd0f7cb97|DZSB{\"id\":55,\"appid\":\"06e91681c05b43b5bd5f1c9dd0f7cb97\",\"insid\":\"ae584ad0844f43629895962bedf1cbf6\",\"eventId\":1558691965876,\"insName\":\"i-2ze3lc1rpbjfoapmkq6u\",\"eventDate\":1558691965876,\"picUrl\":\"https://storage.lianwukeji.com/ai/fall_service/91b524a700d84d438eb718949760c628.jpg\",\"thumbUrl\":\"https://storage.lianwukeji.com/ai/fall_thumbs/ae2c3580f314479bba6047f378072d77.jpg\",\"created\":1558691965975,\"modified\":1558691965975,\"deleted\":false,\"ts\":1558691965975}").getBytes(RemotingHelper.DEFAULT_CHARSET)// body
                    );

                    //调用producer的send()方法发送消息
                    //这里调用的是同步的方式，所以会有返回结果，同时默认发送的也是普通消息
                    SendResult sendResult = producer.send(msg);

                    /**
                     * 这里设置需要延时的等级即可
                      */
                    msg.setDelayTimeLevel(3);

                    //打印返回结果，可以看到消息发送的状态以及一些相关信息
                    System.out.println(sendResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(1000);
                }
//            }

            //发送完消息之后，调用shutdown()方法关闭producer
            producer.shutdown();
        } catch (MQClientException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
