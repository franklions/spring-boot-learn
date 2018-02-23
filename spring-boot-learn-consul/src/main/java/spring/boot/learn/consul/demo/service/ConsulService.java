package spring.boot.learn.consul.demo.service;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.*;
import com.orbitz.consul.model.health.ServiceHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/22
 * @since Jdk 1.8
 */
@Service
public class ConsulService {

    private  Consul _consul ;

    public ConsulService(@Autowired Consul consul){
        this._consul = consul;

    }

    /**
     * 注册服务
     * 并对服务进行健康检查
     * servicename唯一
     * @param serviceName
     * @param serviceId     没发现有什么作用
     */
    public void registerService(String serviceName,String serviceId){
        AgentClient agentClient = _consul.agentClient();     //建立agentClient


        try {
            /**
             * 注意该注册接口：
             * 需要提供一个健康检查的服务URL，以及每隔多长时间访问一下该服务（这里是3s）
             */
            agentClient.register(8080, URI.create("http://localhost:8080/health").toURL(),
                    3, serviceName, serviceId, "dev");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//        try {
//            agentClient.pass(serviceId);          //健康检查
//        } catch (NotRegisteredException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 发现可用的服务
     * @param serviceName
     * @return
     */
    public List<ServiceHealth> discoverHealthyService(String serviceName){
        HealthClient healthClient = _consul.healthClient();  //获取所有健康的服务
        return healthClient.getHealthyServiceInstances(serviceName).getResponse();  //寻找passing状态的节点
    }

    /**
     * 存储KV
     * @param key
     * @param value
     */
    public void storeKV(String key,String value){
        KeyValueClient keyValueClient = _consul.keyValueClient();
        keyValueClient.putValue(key,value);
    }

    /**
     * 根据Key获取value
     * @param key
     * @return
     */
    public String getKV(String key){
        KeyValueClient keyValueClient = _consul.keyValueClient();
        return keyValueClient.getValueAsString(key).get();
    }

    /**
     * 找出一致性的节点（应该是同一个DC中的所有server节点）
     * @return
     */
    public List<String> findRaftPeers(){
        StatusClient statusClient = _consul.statusClient();
        return statusClient.getPeers();
    }

    /**
     * 获取leader
     * @return
     */
    public String findRaftLeader(){
        StatusClient statusClient = _consul.statusClient();
        return statusClient.getLeader();
    }
}
