package com.team.rocket.springdemo.controller;

import com.team.rocket.springdemo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;

    public GreetingController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/hello")
    public void greeting(Message helloMessage) throws Exception {
        Thread.sleep(2_000 );
        String message = "Hello " + helloMessage.getName() + "!";

        simpMessagingTemplate.convertAndSendToUser(helloMessage.getName(), "/topic/students", message);
    }

}
