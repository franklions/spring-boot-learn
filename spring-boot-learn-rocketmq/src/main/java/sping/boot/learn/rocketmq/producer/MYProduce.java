package sping.boot.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-04
 * @since Jdk 1.8
 */
public class MYProduce {
    public static void main(String[] args) {

            //声明并初始化一个producer
            //需要一个producer group名字作为构造方法的参数，这里为concurrent_producer
            DefaultMQProducer producer = new DefaultMQProducer("concurrent_producer");

            //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
            //NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码里
            producer.setNamesrvAddr("47.95.203.60:36789");
        producer.setVipChannelEnabled(false);
        try {
            //调用start()方法启动一个producer实例
            producer.start();


            //发送10条消息到Topic为TopicTest，tag为TagA，消息内容为“Hello RocketMQ”拼接上i的值
            for (int i = 0; i < 1; i++) {
                try {
                    Message msg = new Message("devices-new-device-info",// topic
                            ("{\"deviceId\":\"aca5667a0e6a4ea0\",\"appId\":\"28eb068c62c74a7cbc9999a3a74c90af\",\"modelId\":\"de5709fc2a8b4896802a762eca088952\",\"deviceSn\":null,\"modelBuild\":null,\"password\":\"1012e114fbe64587a3faf42cdfaf39cd\",\"securityLevel\":0,\"addr\":null,\"ts\":1691041850852,\"secretKey\":\"7def709ccc434ad0b41f58dc176735d5\",\"modelName\":\"LORA香烟报警器\",\"isProxy\":0,\"isNetwork\":0,\"ownership\":2,\"ownType\":1,\"modelCode\":\"HY04\",\"commProtocol\":\"lora\",\"dtype\":\"HY04\",\"ctype\":\"1000\",\"dtypeCode\":\"HY04\",\"did3rd\":\"A00015\",\"manufactor\":\"hy\"}" ).getBytes(RemotingHelper.DEFAULT_CHARSET)// body
                    );
                    //调用producer的send()方法发送消息
                    //这里调用的是同步的方式，所以会有返回结果，同时默认发送的也是普通消息

                    SendResult sendResult = producer.send(msg,10000);
                    //打印返回结果，可以看到消息发送的状态以及一些相关信息
                    System.out.println(sendResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(1000);
                }
            }

            //发送完消息之后，调用shutdown()方法关闭producer
            producer.shutdown();
        } catch (MQClientException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
