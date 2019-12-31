package org.hc.demo.controller;

import org.hc.demo.service.ISendeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class StreamTest {

    @Autowired
    private ISendeService sendService;

    @RequestMapping("/send")
    public void testStream() {
        String msg = "hello stream ...";
        // 将需要发送的消息封装为Message对象
        Message message = MessageBuilder.withPayload(msg.getBytes()).build();
        sendService.send().send(message);
    }
}
