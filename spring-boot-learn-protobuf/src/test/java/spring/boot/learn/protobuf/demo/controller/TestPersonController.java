package spring.boot.learn.protobuf.demo.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import spring.boot.learn.protobuf.demo.protocol.AddressBookProtos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/16
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
public class TestPersonController {
    @Test
    public void testAddPerson() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8088/person");

//        UserProto.User user =   UserProto.User.newBuilder().setId(1).setName("zhangsan").addPhone(UserProto.User.PhoneNumber.newBuilder().setNumber("18611163408")).build();//构造
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder().setId(12837)
                .setName("flsh").setEmail("feng@jiuan.com").build();
        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(addressBook.toByteArray());
        InputStreamEntity inputStreamEntity = new InputStreamEntity(inputStream);

//这两行很重要的，是告诉springmvc客户端请求和响应的类型，指定application/x-protobuf类型,spring会用ProtobufHttpMessageConverter类来解析请求和响应的实体
        httpPost.addHeader("Content-Type","application/x-protobuf");
        httpPost.addHeader("Accept", "application/x-protobuf");

httpPost.setEntity(inputStreamEntity);
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {

            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();

            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            entity2.writeTo(buf);
            System.out.println(new String(buf.toByteArray())+"#################");
            AddressBookProtos.Person user2 = AddressBookProtos.Person.parseFrom(buf.toByteArray());
            System.out.println(user2);
        } finally {
            response2.close();
        }
    }
}
