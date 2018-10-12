package com.example.demo.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket握手（handshake）接口
 * Created by earl on 2017/4/17.
 */
@Component
public class HttpSessionIdHandshakeInterceptor extends HttpSessionHandshakeInterceptor  {


    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        //解决The extension [x-webkit-deflate-frame] is not supported问题
        if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        //检查session的值是否存在
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);

            System.out.println("beforeHandshake1---:"+servletRequest.getHeaders().toString());
            System.out.println("beforeHandshake2---:"+response.getHeaders().toString());
            System.out.println("beforeHandshake3---:"+attributes);
//            System.out.println("beforeHandshake2---:"+session.getAttribute());

        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }


    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        System.out.println("HttpSessionIdHandshakeInterceptor---------afterHandshake");
        super.afterHandshake(request, response, wsHandler, ex);

    }


}

