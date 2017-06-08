package elasticsearch.client.demo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/7
 * @since Jdk 1.8
 */
public class TransportClientConfig   {
    /**
     * TransportClient利用transport模块远程连接一个elasticsearch集群。
     * 它并不加入到集群中，只是简单的获得一个或者多个初始化的transport地址，
     * 并以轮询的方式与这些地址进行通信。
     * @return
     */
    private  static TransportClient client = null;

    public static TransportClient getTransportClient()  {


//        Settings settings = Settings.builder()
//                .put("cluster.name", "myClusterName")   // 如果你有一个与elasticsearch集群不同的集群，你可以设置机器的名字。
//                .put("client.transport.sniff", true).build();  //这个客户端可以嗅到集群的其它部分，并将它们加入到机器列表。为了开启该功能，设置client.transport.sniff为true。
        if(client == null){
            try {
                client = new PreBuiltTransportClient(Settings.EMPTY)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.63.128"), 9300));

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return client;
    }
}
