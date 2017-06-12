package elasticsearch.client.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Date;

import static elasticsearch.client.demo.config.TransportClientConfig.getTransportClient;
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

    private static final String INDEX_NAME = "megacorp";
    private static final String TYPE_NAME="employee";

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
           ListenableActionFuture<IndexResponse> responseFuture= getTransportClient()
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

    public static <T> boolean indexDocument( String id, T source) {

        try {

            String docJsonStr = mapper.writeValueAsString(source);

            IndexResponse response= getTransportClient()
                    .prepareIndex(INDEX_NAME,TYPE_NAME)
                    .setId(id)
                    .setSource(docJsonStr)
                    .execute().actionGet();
            return response.status().getStatus() > 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDocument(String id) {
        DeleteResponse response = getTransportClient().prepareDelete(INDEX_NAME,TYPE_NAME,id).execute().actionGet();

        return response.status().getStatus() > 0;
    }

    public static <T> T getDocument(String id,TypeReference<T> typeref) {
        GetResponse response = getTransportClient().prepareGet(INDEX_NAME,TYPE_NAME,id).execute().actionGet();

        T instance = null;
        try {
            instance = mapper.readValue(response.getSourceAsString(),typeref);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return   instance;
    }

    public static <T> boolean updateDocument( String id, T source) {

        try {

            String docJsonStr = mapper.writeValueAsString(source);

            UpdateResponse response= getTransportClient()
                    .prepareUpdate(INDEX_NAME,TYPE_NAME,id)
                    .setDoc(docJsonStr)
                    .execute().actionGet();
            return response.status().getStatus() > 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
