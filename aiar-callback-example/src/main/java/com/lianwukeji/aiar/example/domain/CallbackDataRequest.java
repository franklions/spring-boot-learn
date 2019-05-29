package com.lianwukeji.aiar.example.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-10
 * @since Jdk 1.8
 */
public class CallbackDataRequest {

    private String msgId;
    private Integer msgType;
    private String toAppId;
    private List<Object> content;
    private Long ts;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getToAppId() {
        return toAppId;
    }

    public void setToAppId(String toAppId) {
        this.toAppId = toAppId;
    }

    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return "CallbackDataRequest{" +
                    "msgId='" + msgId + '\'' +
                    ", msgType=" + msgType +
                    ", toAppId='" + toAppId + '\'' +
                    ", content=" + objectMapper.writeValueAsString( content) +
                    ", ts=" + ts +
                    '}';
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "CallbackDataRequest{" +
                "msgId='" + msgId + '\'' +
                ", msgType=" + msgType +
                ", toAppId='" + toAppId + '\'' +
                ", content=null" +
                ", ts=" + ts +
                '}';
    }
}
