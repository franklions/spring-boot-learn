package spring.boot.learn.aliyun.log.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/6
 * @since Jdk 1.8
 */
@ConfigurationProperties("aliyun.client")
public class AliyunClientProperties {
    private String endpoint ; // 选择与上面步骤创建 project 所属区域匹配的
    private  String accessKeyId ; // 使用您的阿里云访问密钥 AccessKeyId
    private String accessKeySecret ; // 使用您的阿里云访问密钥

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
