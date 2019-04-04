package sping.boot.learn.rocketmq.tools;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.admin.TopicStatsTable;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;

import java.util.UUID;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-04
 * @since Jdk 1.8
 */
public class MQAdminExtTest {

    private static  DefaultMQAdminExt mqAdminExt=null;
    private static String nameSrvAddr = "39.107.65.249:9876;47.94.21.223:9876";
    static {
        mqAdminExt = new DefaultMQAdminExt();
        mqAdminExt.setInstanceName(UUID.randomUUID().toString());
        mqAdminExt.setNamesrvAddr(nameSrvAddr);
        try {
            mqAdminExt.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ClusterInfo clusterInfo = null;

            clusterInfo = getCluster();
            String[] clusterName = clusterInfo.retrieveAllClusterNames();
            if(clusterName == null || clusterName.length <1)
            {
                System.out.println("cluster is null ");
                mqAdminExt.shutdown();
                return ;
            }

            String newTopicName="FFTestName";
            for (String name :
                    clusterName) {
                System.out.println("cluster name:"+name);
                createTopic(name, newTopicName, 1);
            }
           boolean result = checkTopicExist(newTopicName);
            System.out.println("Topic exist:"+ result);


        mqAdminExt.shutdown();
    }

    public static ClusterInfo getCluster() {

        ClusterInfo clusterInfo = null;
        try {
            clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            clusterInfo.retrieveAllClusterNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clusterInfo;
    }

    private static void createTopic(String clusterName, String topic,int queueNum){
        try {
            mqAdminExt.createTopic(clusterName, topic, queueNum);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkTopicExist(String topic) {
        boolean createResult = false;
        try {
            TopicStatsTable topicInfo = mqAdminExt.examineTopicStats(topic);
            createResult = !topicInfo.getOffsetTable().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return createResult;
    }
}
