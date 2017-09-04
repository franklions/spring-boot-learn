//package spring.boot.learn.rabbitmq.demo.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.Scope;
//import spring.boot.learn.rabbitmq.demo.service.MessageReceiver;
///**
// * @author Administrator
// * @version 1.0
// * @description
// * @date 2017/7/25
// * @since Jdk 1.8
// */
//@Configuration
//public class SecondRabbitmqConfig {
//    @Bean("secondConnectionFactory")
//    @Primary
//    public ConnectionFactory secondConnectionFactory(@Value("${spring.rabbitmq.second.host}") String host,
//                                                    @Value("${spring.rabbitmq.second.port}") int port,
//                                                    @Value("${spring.rabbitmq.second.username}") String username,
//                                                    @Value("${spring.rabbitmq.second.password}") String password) {
//        CachingConnectionFactory connFactory = new CachingConnectionFactory();
//        connFactory.setHost(host);
//        connFactory.setPort(port);
//        connFactory.setUsername(username);
//        connFactory.setPassword(password);
//        connFactory.setVirtualHost("test");
//        connFactory.setPublisherConfirms(true); //必须要设置
//        return connFactory;
//    }
//
//
//    @Bean("secondRabbitListenerContainerFactory")
//    public SimpleRabbitListenerContainerFactory secondRabbitListenerContainerFactory(
//            SimpleRabbitListenerContainerFactoryConfigurer configurer,
//            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        configurer.configure(factory,connectionFactory);
//        return factory;
//    }
//    @Bean(name="secondRabbitTemplate")
//    public RabbitTemplate secondRabbitTemplate(
//            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory
//    ){
//        RabbitTemplate secondRabbitTemplate = new RabbitTemplate(connectionFactory);
//        return secondRabbitTemplate;
//    }
//
//    /**
//     * 针对消费者配置
//     * 1. 设置交换机类型
//     * 2. 将队列绑定到交换机
//     *
//     *
//     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
//     HeadersExchange ：通过添加属性key-value匹配
//     DirectExchange:按照routingkey分发到指定队列
//     TopicExchange:多关键字匹配
//     */
//    @Bean("secondDirectExchange")
//    public DirectExchange secondDirectExchange(@Value("${spring.rabbitmq.second.exchange}") String exchange){
//        return new DirectExchange(exchange);
//    }
//
//    /**
//     * 创建队列
//     * @return
//     */
//    @Bean("secondQueue")
//    public Queue secondQueue(@Value("${spring.rabbitmq.second.queue}") String queue){
//        return new Queue(queue,true);   //队列持久
//    }
//
//    /**
//     * 创建交换机与队列绑定关系
//     * @return
//     */
//    @Bean("secondBinding")
//    public Binding secondBinding(@Qualifier("secondQueue")Queue secondQueue,
//                                @Qualifier("secondDirectExchange")DirectExchange secondDirectExchange,
//                                @Value("${spring.rabbitmq.second.routingKey}") String routingKey
//    ) {
//        return BindingBuilder.bind(secondQueue).to(secondDirectExchange).with(routingKey);
//    }
//
//
//    /**
//     * 消息监听器
//     * @return
//     */
//    @Bean("secondContainer")
//    public SimpleMessageListenerContainer secondContainer(ApplicationContext applicationContext,
//                                                         @Qualifier("secondQueue")Queue secondQueue
//            ,  @Qualifier("secondRabbitListenerContainerFactory") SimpleRabbitListenerContainerFactory secondRabbitListenerContainerFactory ){
////        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(secondConnectionFactory);
//        MessageReceiver receiver = applicationContext.getBean(MessageReceiver.class);
//        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
//        endpoint.setMessageListener(receiver);
//        SimpleMessageListenerContainer container = secondRabbitListenerContainerFactory.createListenerContainer(endpoint);
//        container.setQueues(secondQueue);
//        container.setExposeListenerChannel(true );
//        container.setMaxConcurrentConsumers(2);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);       //设置确认模式手工模式
//        return container;
//    }
//}
