package com.lianwukeji.aiar.example;

import com.lianwukeji.aiar.example.domain.Journal;
import com.lianwukeji.aiar.example.repository.JournalRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-05-23
 * @since Jdk 1.8
 */
@SpringBootApplication
public class RunApplication {

    @Bean
    InitializingBean saveData(JournalRepository repo){
        return ()->{
            repo.save(new Journal("Get to know Spring Boot","Today I will learn Spring Boot","01/01/2016"));
            repo.save(new Journal("Simple Spring Boot Project","I will do my first Spring Boot Project","01/02/2016"));
            repo.save(new Journal("Spring Boot Reading","Read more about Spring Boot","02/01/2016"));
            repo.save(new Journal("Spring Boot in the Cloud","Spring Boot using Cloud Foundry","03/01/2016"));
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class,args);
    }
}
