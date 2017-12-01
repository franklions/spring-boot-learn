package spring.boot.learn.aliyun.log.demo.AliyunService;

import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessor;
import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessorFactory;

/**
 * @author flsh
 * @version 1.0
 * @description
 * Iot应用日志消费者创建工厂
 * @date 2017/11/6
 * @since Jdk 1.8
 */
public class IotAppLogHubProcessorFactory implements ILogHubProcessorFactory {


    @Override
    public ILogHubProcessor generatorProcessor() {
        return  new IotAppLogHubProecessor();
    }
}
