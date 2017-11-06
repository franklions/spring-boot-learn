package spring.boot.learn.aliyun.log.demo.config;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.loghub.client.ClientWorker;
import com.aliyun.openservices.loghub.client.config.LogHubConfig;
import com.aliyun.openservices.loghub.client.exceptions.LogHubClientWorkerException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.boot.learn.aliyun.log.demo.AliyunService.IotAppLogHubProcessorFactory;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/6
 * @since Jdk 1.8
 */
@Configuration
@EnableConfigurationProperties({AliyunClientProperties.class,LogHubProperties.class})
public class AppConfig {

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client aliyunClient(AliyunClientProperties aliyunClientProperties){
        Client client = new Client(aliyunClientProperties.getEndpoint(),
                aliyunClientProperties.getAccessKeyId(),
                aliyunClientProperties.getAccessKeySecret());
        return client;
    }

    @Bean
    public IotAppLogHubProcessorFactory iotAppLogHubProcessorFactory(){
        return new IotAppLogHubProcessorFactory();
    }

    @Bean
    public LogHubConfig logHubConfig(LogHubProperties logHubProperties){
        LogHubConfig config = new LogHubConfig(logHubProperties.getmConsumerGroupName(),
                logHubProperties.getmWorkerInstanceName(),logHubProperties.getmLogHubEndPoint(),
                logHubProperties.getmProject(),logHubProperties.getmLogStore(),logHubProperties.getmAccessId(),
                logHubProperties.getmAccessKey(),logHubProperties.getmCursorPosition(),
                logHubProperties.getmHeartBeatIntervalMillis(),logHubProperties.ismConsumeInOrder());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(ClientWorker.class)
    public ClientWorker clientWorker(IotAppLogHubProcessorFactory iotAppLogHubProcessorFactory,
                                     LogHubConfig logHubConfig){
        ClientWorker worker = null;
        try {
            worker = new ClientWorker(iotAppLogHubProcessorFactory, logHubConfig);
        } catch (LogHubClientWorkerException e) {
            e.printStackTrace();
        }
        return worker;
    }


}
