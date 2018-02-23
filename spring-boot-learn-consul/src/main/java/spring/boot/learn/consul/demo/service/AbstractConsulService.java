package spring.boot.learn.consul.demo.service;


import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.apache.log4j.Logger;

/**
 * @author flsh
 * @version 1.0
 * @description
 * consul的基类，用于构建Consl对象，服务于服务端以及客户端
 * @date 2018/2/23
 * @since Jdk 1.8
 */
public class AbstractConsulService {
        private static final Logger logger = Logger.getLogger(AbstractConsulService.class);

    protected final static String CONSUL_NAME="consul_node_jim";
    protected final static String CONSUL_ID="consul_node_id";
    protected final static String CONSUL_TAGS="v3";
    protected final static String CONSUL_HEALTH_INTERVAL="1s";

    protected Consul buildConsul(String registryHost, int registryPort){
        return Consul.builder().withHostAndPort(HostAndPort.fromString(registryHost+":"+registryPort)).build();
    }
}
