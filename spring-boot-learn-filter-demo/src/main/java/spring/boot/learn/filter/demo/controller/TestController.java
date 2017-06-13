package spring.boot.learn.filter.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
@RestController
public class TestController {
    @PostMapping("/test")
    public String test(@RequestParam("param") String str,@RequestBody Object body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString( body));
        return  "<"+str + ">test success!";
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity("内部服务器错误",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
