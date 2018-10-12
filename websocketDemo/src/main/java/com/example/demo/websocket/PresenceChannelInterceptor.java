package com.example.demo.websocket;
//import club.yelv.component.CacheManager;
//import com.demo.websocket.constants.CacheConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        // ignore non-STOMP messages like heartbeat messages
        if(sha.getCommand() == null) {
            return;
        }
        Map map = (Map) message.getHeaders().get("nativeHeaders");
        if(map!=null) {
            LinkedList list = (LinkedList) map.get("user");
            if (list != null)
                System.out.println("postSend------------>" + list.get(0));
        }
        //这里的sessionId和accountId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
//        String sessionId = sha.getSessionAttributes().get(Constants.SESSIONID).toString();
//        String accountId = sha.getSessionAttributes().get(Constants.SKEY_ACCOUNT_ID).toString();
        //判断客户端的连接状态
        switch(sha.getCommand()) {
            case CONNECT:
//                connect(sessionId,accountId);
                System.out.println("PresenceChannelInterceptor-------连接成功");
                break;
            case CONNECTED:
                break;
            case DISCONNECT:
//                disconnect(sessionId,accountId,sha);
                System.out.println("PresenceChannelInterceptor-------断开连接");
                break;
            default:
                break;
        }
    }

    //连接成功
    private void connect(String sessionId,String accountId){
        System.out.println("PresenceChannelInterceptor-----connect------sessionId:"+sessionId+"--accountId:"+accountId);
        logger.debug(" STOMP Connect [sessionId: " + sessionId + "]");
        //存放至ehcache
        String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
        //若在多个浏览器登录，直接覆盖保存
//        CacheManager.put(cacheName ,cacheName+accountId,sessionId);
    }

    //断开连接
    private void disconnect(String sessionId,String accountId, StompHeaderAccessor sha){
        System.out.println("PresenceChannelInterceptor------disconnect-----sessionId:"+sessionId+"--accountId:"+accountId);
        logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
        sha.getSessionAttributes().remove(Constants.SESSIONID);
        sha.getSessionAttributes().remove(Constants.SKEY_ACCOUNT_ID);
        //ehcache移除
//        String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
//        if (CacheManager.containsKey(cacheName,cacheName+accountId) ){
//            CacheManager.remove(cacheName ,cacheName+accountId);
//        }

    }

}

