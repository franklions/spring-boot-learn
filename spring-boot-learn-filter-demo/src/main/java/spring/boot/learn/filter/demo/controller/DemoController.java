package spring.boot.learn.filter.demo.controller;

import com.iemylife.iot.webtoolkit.HttpResult;
import com.iemylife.iot.webtoolkit.IotHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
@RestController
public class DemoController {

    @Autowired
    IotHttp iotHttp;

    @PostMapping("/demo/{title}")
    public String demo(@PathVariable String title, @RequestBody Object body,@RequestHeader HttpHeaders headers){

        HttpResult<String> result = iotHttp.post("http://localhost:8808/test?param=1222",null,String.class);

        return  result.getValue().get();
    }
}
