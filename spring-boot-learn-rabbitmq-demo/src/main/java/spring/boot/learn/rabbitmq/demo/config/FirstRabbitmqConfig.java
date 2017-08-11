package spring.boot.learn.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import spring.boot.learn.rabbitmq.demo.service.MessageReceiver;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/25
 * @since Jdk 1.8
 */
@Configuration
public class FirstRabbitmqConfig {

    @Bean("firstConnectionFactory")
    public ConnectionFactory firstConnectionFactory(
                                                    @Value("${spring.rabbitmq.first.addresses}") String addresses,
                                                    @Value("${spring.rabbitmq.first.username}") String username,
                                                    @Value("${spring.rabbitmq.first.password}") String password) {
        CachingConnectionFactory connFactory = new CachingConnectionFactory();
        connFactory.setAddresses(addresses);
        connFactory.setUsername(username);
        connFactory.setPassword(password);
        connFactory.setVirtualHost("/");
        connFactory.setPublisherConfirms(true); //必须要设置
        return connFactory;
    }

    @Bean("firstRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory firstRabbitListenerContainerFactory(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        return factory;
    }


    @Bean(name="firstRabbitTemplate")
    public RabbitTemplate firstRabbitTemplate(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory
    ){
        RabbitTemplate firstRabbitTemplate = new RabbitTemplate(connectionFactory);
        return firstRabbitTemplate;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     *
     *
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean("firstDirectExchange")
    public DirectExchange firstDirectExchange(@Value("${spring.rabbitmq.first.exchange}") String exchange){
        return new DirectExchange(exchange);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean("firstQueue")
    public Queue firstQueue(@Value("${spring.rabbitmq.first.queue}") String queue){
        return new Queue(queue,true);   //队列持久
    }

    /**
     * 创建交换机与队列绑定关系
     * @return
     */
    @Bean("firstBinding")
    public Binding firstBinding(@Qualifier("firstQueue")Queue firstQueue,
                                @Qualifier("firstDirectExchange")DirectExchange firstDirectExchange,
                                @Value("${spring.rabbitmq.first.routingKey}") String routingKey
    ) {
        return BindingBuilder.bind(firstQueue).to(firstDirectExchange).with(routingKey);
    }


    /**
     * 消息监听器
     * @return
     */
    @Bean("firstContainer")
    public SimpleMessageListenerContainer firstContainer(ApplicationContext applicationContext,
                                                    @Qualifier("firstQueue")Queue firstQueue
                                                    ,@Qualifier("firstConnectionFactory")  ConnectionFactory firstConnectionFactory,
                                                         @Qualifier("firstRabbitListenerContainerFactory") SimpleRabbitListenerContainerFactory firstRabbitListenerContainerFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(firstConnectionFactory);

        container.setQueues(firstQueue);
        container.setExposeListenerChannel(true );
        container.setMaxConcurrentConsumers(1);

        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);       //设置确认模式手工模式
//        DeviceStateReceiver receiver = new DeviceStateReceiver();
        MessageReceiver receiver = applicationContext.getBean(MessageReceiver.class);
        container.setMessageListener(receiver);

        return container;
    }

}
