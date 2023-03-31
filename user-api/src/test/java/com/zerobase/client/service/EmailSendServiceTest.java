package com.zerobase.client.service;

import com.zerobase.client.MailgunClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;

    @Test
    public void EmailTest() {

        // mailgunClient.sendEmail();
        // String response = emailSendService.sendEmail();
    }
}