package spring.boot.learn.protobuf.demo.controller;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.learn.protobuf.demo.protocol.AddressBookProtos.Person;

/**
 * @author flsh
 * @version 1.0
 * @description
 * 1.如果请求的参数不能映射到相应的实体上，
 * 则会产生异常<HttpRequestMethodNotSupportedException>
 *
 * @date 2018/1/16
 * @since Jdk 1.8
 */
@RestController
public class PersonController {
    @PostMapping(value = "/person", consumes="application/x-protobuf",
            produces = "application/x-protobuf")
    public ResponseEntity<Person> addPerson(RequestEntity<Person> personRequestEntity){
        Person user =  personRequestEntity.getBody();
        System.out.println("server===\n" + user);
        return ResponseEntity.ok(user);
    }
}
