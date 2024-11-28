package com.momo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PingService {
    /**
     * 1 sec to ping
     */
    @Scheduled(cron = "0/1 * * * * *")
    public void ping() throws FileNotFoundException {
        RandomAccessFile file = null;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        file = new RandomAccessFile("D:/a.lock", "rw");
        checkFileLock(now, file);
    }

    public void checkFileLock(String now, RandomAccessFile file) {
        FileLock lock = null;
        try {
            lock = file.getChannel().lock();
            String str = null;
            try {
                str = file.readUTF();
            } catch (Exception e) {
            }
            file.setLength(0);
            if (StringUtils.isEmpty(str)) {
                str = now + "-1";
            }
            if (!(now + "-2").equals(str)) {
                //to pong server
                toPong();
                str = now + "-2";
                file.writeUTF(str);
                if (lock != null && lock.isValid()) {
                    lock.release();
                }
            } else {
                System.out.println("rate limit");
                if (lock != null && lock.isValid()) {
                    lock.release();
                }
            }
        } catch (IOException e) {
            System.out.println("send ping err!");
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private void toPong() {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        System.out.println("send Hello to Pong server!");
        client.execute(URI.create("ws://localhost:8080/pong"), session -> session.send(Flux.just(session.textMessage("Hello")))
                .thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))
                .doOnNext(s -> System.out.println("received msg:" + s))
                .then()
        ).onTerminateDetach().doOnError(throwable -> System.out.println("error!")).subscribe();
    }
}
