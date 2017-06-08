package spring.boot.learn.config.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import spring.boot.learn.config.demo.config.MyConfig;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/5/26
 * @since Jdk 1.8
 */
@SpringBootApplication

public class ConfigDemoApplication implements CommandLineRunner {
    @Autowired
    MyConfig myConfig;

    public static void main(String[] args) {
        SpringApplication.run(ConfigDemoApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(">>>>>>>>>valuekey="+myConfig.getValueKey());
        System.out.println(">>>>>>>>>apiKey="+myConfig.getProperties().get("apikey"));
        System.out.println(">>>>>>>>>apiurl="+myConfig.getProperties().get("apiurl"));
        System.out.println(">>>>>>>>>strkey="+myConfig.getStrKey());
        System.out.println(">>>>>>>>>lsitvalue="+myConfig.getListProps().get(0));
        System.out.println(">>>>>>>>>lsitsize="+myConfig.getListProps().size());
        System.out.println(">>>>>>>>>intvalue="+myConfig.getIntValue());
        System.out.println(">>>>>>>>>注解式Int类型值="+myConfig.getSysIntValue());
    }
}
