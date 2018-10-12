package com.example.demo.controller;

import com.example.demo.entity.BroadcastMessage;
import com.example.demo.entity.WiselyMessage;
import com.example.demo.entity.WiselyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final String topicAddr = "/topic/broadcast";

    private static final  String userAddr = "/warn";

    @MessageMapping("/welcome")
//    @SendTo("/topic/getResponse")
    public void say(WiselyMessage wiselyMessage){
        System.out.println("------------connected-------"+wiselyMessage);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        messagingTemplate.convertAndSend(topicAddr,new WiselyResponse("Welcome,"+wiselyMessage.getMessage()));
        messagingTemplate.convertAndSendToUser(wiselyMessage.getDestinationName(),userAddr,new WiselyResponse("welcome,"+wiselyMessage.getDestinationName()));
//        return new WiselyResponse("Welcome,"+wiselyMessage.getMsg());
    }
    @MessageMapping("/sendWarn")
    public void sendToUser(WiselyMessage wiselyMessage){

    }
    @MessageMapping("/broadcastMessage")
    public void broadcastMessage(BroadcastMessage message){
        messagingTemplate.convertAndSend(topicAddr,new WiselyResponse(message.getMessage()));
    }
}
