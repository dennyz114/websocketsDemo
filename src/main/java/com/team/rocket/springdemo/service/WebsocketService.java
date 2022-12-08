package com.team.rocket.springdemo.service;

import com.team.rocket.springdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendNewStudent(Student newStudent) {
        simpMessagingTemplate.convertAndSend("/topic/students", newStudent);
    }
}
