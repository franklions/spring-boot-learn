package spring.boot.learn.resttemplate.demo.controller;

import com.iemylife.iot.spring.http.HttpResult;
import com.iemylife.iot.spring.http.IotHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/7/11
 * @since Jdk 1.8
 */
@RestController
public class TestIotHttpController {

    @Autowired
    IotHttp  iotHttp;

    @GetMapping("/doGet")
    public ResponseEntity<?> doGet(){

        String url = "http://47.94.245.54:8080/v0/user_service/users/tokens/authorize";
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("uid","asdfasdfa");
        headers.add("token","aasdfasdf");
        headers.add("clientType","1");
        headers.add("appid","asdfasdf");
        HttpResult<String> respValue = iotHttp.doGet(url,headers,String.class,null);

        if(respValue.isFail()){
            System.out.printf("Fail");
        }else
        {
            System.out.println("exp:    "+            respValue.getRealValue());
        }

        return ResponseEntity.ok("success");
    }
}
