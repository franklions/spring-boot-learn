package spring.boot.learn.config.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/25
 * @since Jdk 1.8
 */
@ConfigurationProperties("my.test")
public class MyConfigProperties {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
