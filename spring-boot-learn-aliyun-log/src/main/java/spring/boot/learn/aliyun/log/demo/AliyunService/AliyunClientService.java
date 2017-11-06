package spring.boot.learn.aliyun.log.demo.AliyunService;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.*;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.*;
import com.aliyun.openservices.log.response.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/10/30
 * @since Jdk 1.8
 */
@Component
public class AliyunClientService {

    static final String endpoint = "cn-beijing.log.aliyuncs.com"; // 选择与上面步骤创建 project 所属区域匹配的
    // Endpoint
    static final String accessKeyId = "LTAI7oxRGBQuiRu8"; // 使用您的阿里云访问密钥 AccessKeyId
    static final String accessKeySecret = "ekiP6UQZYFqFrTf8DehPdlwjMebUpn"; // 使用您的阿里云访问密钥

    private static  Client innerClient = null;

    static{
        innerClient = new Client(endpoint,accessKeyId,accessKeySecret);
    }

    public AliyunClientService() {
    }

    public void getLogstore(String project, String logStoreName){
        GetLogStoreRequest request = new GetLogStoreRequest(project,logStoreName);
        try {
            GetLogStoreResponse response = innerClient.GetLogStore(request);
            LogStore logStore = response.GetLogStore();
            if(logStore != null ){
                System.out.println(logStore.ToJsonString());
            }
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    public void getListLogstores(String project,Integer offset,Integer size,String logStoreName){
        ListLogStoresRequest request = new ListLogStoresRequest(project,offset,size,logStoreName);
        ArrayList<String> logStoreNames = null;
        try {
            ListLogStoresResponse response =  innerClient.ListLogStores(request);
            logStoreNames = response.GetLogStores();
            System.out.println( "ListLogs:" + logStoreNames.toString() + "\n");
            System.out.println("ListLogsTotal:"+response.GetCount());
        } catch (LogException e) {
            e.printStackTrace();
        }

    }

    public void getListShards(String project,String logStoreName){
        ListShardRequest request = new ListShardRequest(project,logStoreName);
        try {
            ListShardResponse response = innerClient.ListShard(request);
            ArrayList<Shard> shards = response.GetShards();
            System.out.println("shards:"+ shards.toString());
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    public void getCursor(String project,String logStoreName,Integer shardId,Long fromTime){
        GetCursorRequest request =null;
        if(fromTime == null ) {
            request = new GetCursorRequest(project,logStoreName,shardId, Consts.CursorMode.END);
       }else{
            request = new GetCursorRequest(project,logStoreName,shardId,fromTime);
       }

        try {
            GetCursorResponse response =  innerClient.GetCursor(request);
            String cursor= response.GetCursor();
            System.out.println("cursor:"+cursor);
        } catch (LogException e) {
            e.printStackTrace();
        }
    }


    public void batchGetLogs(String project,String logStoreName,Integer shardId,
                              Integer count,String cursor,String endCursor){
        BatchGetLogRequest request = new BatchGetLogRequest(project
                ,logStoreName,shardId,count,cursor,endCursor);

        try {
            BatchGetLogResponse response = innerClient.BatchGetLog(request);
            System.out.println("count:"+response.GetCount() + ",size:"+response.GetRawSize());
            List<LogGroupData> logGroups = response.GetLogGroups();
            for(LogGroupData logGroupData:logGroups){
                   Logs.LogGroup logGroup = logGroupData.GetLogGroup();
                System.out.println(String.format("\tcategory\t:\t%s\n\tsource\t:\t%s\n\ttopic\t:\t%s\n\tmachineUUID\t:\t%s",
                        logGroup.getCategory(), logGroup.getSource(), logGroup.getTopic(),logGroup.getMachineUUID()));

                if( logGroup.getLogsCount() >0){
                        for(Logs.Log log : logGroup.getLogsList()){
                           if(log.getContentsCount() >0){
                               for(Logs.Log.Content logContent: log.getContentsList()){
                                   System.out.println(logContent.getKey() + "\t:\t" + logContent.getValue() +"\n");
                               }
                           }
                        }
                    }
            }
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    public void getLogs(String project,String logStoreName,Integer from,Integer to,
                         String topic,String query){
        GetLogsRequest request = new GetLogsRequest(project,logStoreName,from,to,topic,query);
        try {
            GetLogsResponse response = innerClient.GetLogs(request);
            if(response != null && response.IsCompleted()){
                if(response.GetCount() >0){
                    for(QueriedLog queriedLog:response.GetLogs()){
                        System.out.println("Source\t:\t"+ queriedLog.GetSource() +"\n");
                        LogItem logItem = queriedLog.GetLogItem();
                        System.out.println("LogItem\t:\t"+logItem.ToJsonString()+"\n");
                        for(LogContent logContent:logItem.GetLogContents()){
                            System.out.println(logContent.GetKey() + "\t:\t" + logContent.GetValue() +"\n");
                        }
                    }
                }
            }
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    public void getHistograms(String project,String logStoreName,String topic,
                               String query,Integer from,Integer to){
        GetHistogramsRequest request = new GetHistogramsRequest(project
                ,logStoreName,topic,query,from,to);
        try {
            GetHistogramsResponse response = innerClient.GetHistograms(request);
            if(response != null && response.IsCompleted()){
                if(response.GetTotalCount() >0){
                    for(Histogram histogram:response.GetHistograms()){
                        System.out.printf("from %d, to %d, count %d.\n", histogram.GetFrom(), histogram.GetTo(), histogram.GetCount());
                    }
                }
            }
        } catch (LogException e) {
            e.printStackTrace();
        }

    }


}
