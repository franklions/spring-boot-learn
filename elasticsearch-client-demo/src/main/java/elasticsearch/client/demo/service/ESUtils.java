package elasticsearch.client.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elasticsearch.client.demo.config.TransportClientConfig;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/7
 * @since Jdk 1.8
 */
public class ESUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 下面的例子将JSON文档索引为一个名字为“twitter”，类型为“tweet”，id值为1的索引。
     */
    public static void createIndex(){
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .field("user", "jerry")
                    .field("postDate", new Date())
                    .field("message", "Hi Elasticsearch")
                    .endObject();
           ListenableActionFuture<IndexResponse> responseFuture= TransportClientConfig.getTransportClient()
                    .prepareIndex("twitter","tweet","3")
                    .setSource(builder)
                    .execute();
             responseFuture.addListener(new ActionListener<IndexResponse>() {
                 @Override
                 public void onResponse(IndexResponse indexResponse) {
                     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>response>>>>>>>");
                     ObjectMapper mapper = new ObjectMapper();
                     try {
                             System.out.println(">>>>>>>>>>>>>index response:"+ mapper.writeValueAsString(indexResponse));
                             // Index name
                             String _index = indexResponse.getIndex();
                             // Type name
                             String _type = indexResponse.getType();
                             // Document ID (generated or not)
                             String _id = indexResponse.getId();
                             // Version (if it's the first time you index this document, you will get: 1)
                             long _version = indexResponse.getVersion();

                     } catch (JsonProcessingException e) {
                         e.printStackTrace();
                     }
                 }

                 @Override
                 public void onFailure(Exception e) {
                     System.out.println(">>>>>>>>>>>>>>>>>exception>>>>>>>>>>>>>>>>");
                     e.printStackTrace();
                 }
             });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
