package com.team.rocket.springdemo.interceptor;

import java.security.Principal;

public class WebsocketUser implements Principal {

    private String name;

    public WebsocketUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
