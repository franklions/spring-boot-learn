package com.lianwukeji.aiar.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lianwukeji.aiar.example.domain.CallbackActionEntity;
import com.lianwukeji.aiar.example.domain.CallbackDataRequest;
import com.lianwukeji.aiar.example.repository.ActionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-05-23
 * @since Jdk 1.8
 */
@RestController
public class CallbackController {

    Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody CallbackDataRequest request) {

        logger.info(request.toString());

        if (request.getMsgType().equals(11)) {
            List<CallbackActionEntity> actionEntityList= new ArrayList<>();
            for (Object actobj :
                    request.getContent()) {
                try {
                    String actStr = objectMapper.writeValueAsString(actobj);
                    CallbackActionEntity actEntity = objectMapper.readValue(actStr, CallbackActionEntity.class);
                    actionEntityList.add(actEntity);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            actionRepository.save(actionEntityList);
        }

        return ResponseEntity.ok("SUCCESS");
    }
}
