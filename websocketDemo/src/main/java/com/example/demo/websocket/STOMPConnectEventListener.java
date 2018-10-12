package com.example.demo.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
@Component
public class STOMPConnectEventListener implements ApplicationListener<SessionSubscribeEvent> {


    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        System.out.println("STOMPConnectEventListener----------onApplicationEvent");
    }
}
