package com.team.rocket.springdemo.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.ArrayList;
import java.util.Map;

public class WebsocketStudentInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object rawHeaders = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (rawHeaders instanceof Map) {
                Object userName = ((Map<?, ?>)rawHeaders).get("userName");
                if (userName instanceof ArrayList)
                    accessor.setUser(new WebsocketUser(((ArrayList<?>)userName).get(0).toString()));
            }
        }
        return message;
    }
}
