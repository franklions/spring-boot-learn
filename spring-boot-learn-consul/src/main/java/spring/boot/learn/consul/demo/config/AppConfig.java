package spring.boot.learn.consul.demo.config;


import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/23
 * @since Jdk 1.8
 */
@Configuration
public class AppConfig {
    @Bean
    public Consul consul(){
      return Consul.builder().withHostAndPort(
                HostAndPort.fromParts("127.0.0.1",8500)).build(); //建立consul实例
    }
}
