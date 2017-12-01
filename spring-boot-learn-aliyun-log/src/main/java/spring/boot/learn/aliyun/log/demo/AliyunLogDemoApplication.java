package spring.boot.learn.aliyun.log.demo;

import com.aliyun.openservices.loghub.client.ClientWorker;
import com.aliyun.openservices.loghub.client.config.LogHubConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.boot.learn.aliyun.log.demo.AliyunService.IotAppLogHubProcessorFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/10/30
 * @since Jdk 1.8
 */
@SpringBootApplication
public class AliyunLogDemoApplication implements CommandLineRunner {

    private static final Executor executor = Executors.newFixedThreadPool(5);

    @Autowired
    ClientWorker clientWorker;

    public static void main(String[] args) {
        SpringApplication.run(AliyunLogDemoApplication.class,args);
    }

    @Override
    public void run(String... strings)  {

        System.out.println(">>>>>>>>>>>启动clientWorker>>>>>>>>>>>");
        executor.execute(clientWorker);

        try {
            Thread.sleep(60 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destory(){
        System.out.println(">>>>>>>>>>>>>>结束clientWorker>>>>>>>>>>>");
        clientWorker.shutdown();
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
