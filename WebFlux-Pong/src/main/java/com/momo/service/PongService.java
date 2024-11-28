package com.momo.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PongService implements WebSocketHandler {
    public final ConcurrentMap<String, Boolean> map = new ConcurrentHashMap();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        if (map.get(now) == null) {
            map.clear();
        }
        Mono<Void> send = webSocketSession.send(webSocketSession.receive()
                .map(msg -> condMsg(webSocketSession, msg, now)));
        return send;
    }

    public WebSocketMessage condMsg(WebSocketSession webSocketSession, WebSocketMessage msg, String now) {
        String str = msg.getPayloadAsText();
        if ("Hello".equals(str) && map.get(now) == null) {
            msg = webSocketSession.textMessage("World");
            System.out.println("success");
            map.put(now, true);
        } else {
            msg = webSocketSession.textMessage("Err Code 429");
            System.out.println("code 429");
        }
        return msg;
    }
}
