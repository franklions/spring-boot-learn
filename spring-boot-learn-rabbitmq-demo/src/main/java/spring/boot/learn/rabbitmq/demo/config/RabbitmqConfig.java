package spring.boot.learn.rabbitmq.demo.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/24
 * @since Jdk 1.8
 */
@Configuration
@EnableConfigurationProperties(RabbitmqProperties.class)
public class RabbitmqConfig {
    @Autowired
    RabbitmqProperties properties;

    @Bean(name="firstConnectionFactory")
    public ConnectionFactory firstConnectionFactory(
            @Value("${spring.rabbitmq.first.addresses}") String addresses,
            @Value("${spring.rabbitmq.first.vhost}") String vhost,
//            @Value("${spring.rabbitmq.first.port}") int port,
            @Value("${spring.rabbitmq.first.username}") String username,
            @Value("${spring.rabbitmq.first.password}") String password
    ){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setPort(port);
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        return connectionFactory;
    }

    @Primary
    @Bean(name="secondConnectionFactory")
    public ConnectionFactory secondConnectionFactory(
//            @Value("${spring.rabbitmq.second.host}") String host,
//            @Value("${spring.rabbitmq.second.port}") int port,
            @Value("${spring.rabbitmq.second.addresses}") String addresses,
            @Value("${spring.rabbitmq.second.vhost}") String vhost,
            @Value("${spring.rabbitmq.second.username}") String username,
            @Value("${spring.rabbitmq.second.password}") String password
    ){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setPort(port);
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        return connectionFactory;
    }

    @Bean(name="firstRabbitTemplate")
    public RabbitTemplate firstRabbitTemplate(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory
    ){
        RabbitTemplate firstRabbitTemplate = new RabbitTemplate(connectionFactory);
        return firstRabbitTemplate;
    }

    @Bean(name="secondRabbitTemplate")
    public RabbitTemplate secondRabbitTemplate(
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory
    ){
        RabbitTemplate secondRabbitTemplate = new RabbitTemplate(connectionFactory);
        return secondRabbitTemplate;
    }

    @Bean(name="firstFactory")
    public SimpleRabbitListenerContainerFactory firstFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name="secondFactory")
    public SimpleRabbitListenerContainerFactory secondFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean("firstQueue")
    public Queue firstQueue( ) {
        System.out.println("configuration firstQueue ........................");
        Queue first =  new Queue("hello1");
        return  first;
    }

    @Bean("secondQueue")
    public Queue secondQueue() {
        System.out.println("configuration secondQueue ........................");
        Queue second =  new Queue("hello2");
        return second;
    }

    @Bean(name = "bAmqpAdmin")
    public AmqpAdmin rpdAmqpAdmin(@Qualifier("secondConnectionFactory") ConnectionFactory secondConnectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(secondConnectionFactory);
        admin.setAutoStartup(false);
        admin.declareQueue(secondQueue());
        return admin;
    }

    @Bean(name = "aAmqpAdmin")
    public AmqpAdmin aAmqpAdmin(@Qualifier("firstConnectionFactory") ConnectionFactory firstConnectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(firstConnectionFactory);
        admin.setAutoStartup(false);
        admin.declareQueue(firstQueue());
        return admin;
    }

}
