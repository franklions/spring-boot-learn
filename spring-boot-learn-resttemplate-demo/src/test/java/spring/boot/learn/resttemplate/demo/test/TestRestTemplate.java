package spring.boot.learn.resttemplate.demo.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/5/27
 * @since Jdk 1.8
 */
public class TestRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public  void testRestTemplateMutleFrom(){

        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
        bodyMap.add("key", "test");
        bodyMap.add("mobile", "231243");
        bodyMap.add("tpl_id", "121");
        bodyMap.add("content", "abad");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(bodyMap, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080", request, String.class);


        if (response == null || response.getBody().length() < 1) {
            throw new NullPointerException("供应商返回结果为空");
        }
    }

}
