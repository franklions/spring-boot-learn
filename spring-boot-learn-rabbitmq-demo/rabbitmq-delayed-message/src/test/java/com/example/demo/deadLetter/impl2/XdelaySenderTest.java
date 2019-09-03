package com.example.demo.deadLetter.impl2;

import com.example.demo.deadLetter.entity.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class XdelaySenderTest {

    @Autowired
    XdelaySender xdelaySender;
    @Test
    public void testSend() {
        Booking booking = new Booking();
        booking.setBookingContent("hhaha");
        booking.setBookingName("预定房子");
        booking.setBookingTime(new Date());
        booking.setOperatorName("hellen");
        xdelaySender.send(booking, 2000);
    }

}