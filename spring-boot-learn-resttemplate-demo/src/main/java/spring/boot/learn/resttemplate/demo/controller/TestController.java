package spring.boot.learn.resttemplate.demo.controller;

import com.iemylife.iot.webtoolkit.HttpResult;
import com.iemylife.iot.webtoolkit.IotHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/5/27
 * @since Jdk 1.8
 */
@RestController
public class TestController {

    @Autowired
    IotHttp iotHttp;

    @GetMapping(value = "/json/str")
    public String returnJsonString(){
        return "\"json string\"";
    }

    @GetMapping(value = "/json/call")
    public String CallJsonString(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
       HttpResult<String> response= iotHttp.get("http://localhost:8099/json/str",headers,String.class);
        String value = response.getValue().get();
        System.out.println(">>>>>>>>>>>>>value="+value);
        return value;
    }
}


