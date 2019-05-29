package com.lianwukeji.aiar.example.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-05-23
 * @since Jdk 1.8
 */
@Data
@ToString
@Entity
public class CallbackActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String insid;
    private String eventId;
    private String insName;
    private Date eventDate;
    private String picUrl;
    private String thumbUrl;

    @Transient
    private SimpleDateFormat displayDateFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    public String getEventDateShort(){
        displayDateFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return displayDateFormatter.format(eventDate);
    }
}
