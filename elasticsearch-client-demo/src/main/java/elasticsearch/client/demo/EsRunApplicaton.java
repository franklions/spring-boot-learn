package elasticsearch.client.demo;

import elasticsearch.client.demo.service.ESUtils;

import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/7
 * @since Jdk 1.8
 */
public class EsRunApplicaton {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>create index begin>>>>>>>>>>>>");
        ESUtils.createIndex();

        System.out.println(">>>>>>>>>>>>>>create index end>>>>>>>>>>>>");
        try {
           int a =System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
