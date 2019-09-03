package com.example.demo.deadLetter.impl1;

import com.example.demo.deadLetter.entity.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-09-03
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImmediateSenderTest {

    @Autowired
    ImmediateSender immediateSender;

    @Test
    public void testSend() {
        Booking booking = new Booking();
        booking.setBookingContent("hhaha");
        booking.setBookingName("预定房子");
        booking.setBookingTime(new Date());
        booking.setOperatorName("hellen");
        immediateSender.send(booking, 1000);
    }

}