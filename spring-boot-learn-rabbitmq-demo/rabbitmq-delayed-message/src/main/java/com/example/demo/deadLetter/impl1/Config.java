package com.example.demo.deadLetter.impl1;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
@Configuration
public class Config {

    // 创建一个立即消费队列
    @Bean
    public Queue immediateQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue(Constants.IMMEDIATE_QUEUE, true);
    }

    // 创建一个延时队列
    @Bean
    public Queue delayQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", Constants.IMMEDIATE_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", Constants.IMMEDIATE_ROUTING_KEY);
        return new Queue(Constants.DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange immediateExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange(Constants.IMMEDIATE_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange(Constants.DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding immediateBinding() {
        return BindingBuilder.bind(immediateQueue()).to(immediateExchange()).with(Constants.IMMEDIATE_ROUTING_KEY);
    }

    @Bean
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(deadLetterExchange()).with(Constants.DELAY_ROUTING_KEY);
    }
}
