package spring.boot.learn.config.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/5/26
 * @since Jdk 1.8
 */
@Configuration
@ConfigurationProperties("myconfig")
@EnableConfigurationProperties(MyConfigProperties.class)
public class MyConfig {

    @Value("${systemconfig.value.key}")
    private String valueKey;

    @Value("${systemconfig.int.value.key}")
    private int sysIntValue;

    private int intValue;

    private String strKey ;

    private Map<String,String> properties ;

    private List<String> listProps;

    public String getStrKey() {
        return strKey;
    }

    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    public List<String> getListProps() {
        return listProps;
    }

    public void setListProps(List<String> listProps) {
        this.listProps = listProps;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getValueKey() {
        return valueKey;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public int getSysIntValue() {
        return sysIntValue;
    }

    public void setSysIntValue(int sysIntValue) {
        this.sysIntValue = sysIntValue;
    }
}
