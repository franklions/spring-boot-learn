package spring.boot.learn.protobuf.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/16
 * @since Jdk 1.8
 */
@Configuration
public class WebConfig {
    @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter(){
        return new ProtobufHttpMessageConverter();
    }
}
