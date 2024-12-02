package com.momo.task;

import com.momo.service.ILogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PingTask {
    @Autowired
    private ILogRecordService logRecordService;

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
                rateLimit(lock);
            }
        } catch (Exception e) {
            logRecordService.addLog("send ping err!");
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private void rateLimit(FileLock lock) throws IOException {
        logRecordService.addLog("rate limit");
        if (lock != null && lock.isValid()) {
            lock.release();
        }
    }

    private void toPong() {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        logRecordService.addLog("send Hello to Pong server!");
        client.execute(URI.create("ws://localhost:8080/pong"), session -> session.send(Flux.just(session.textMessage("Hello")))
                .thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))
                .doOnNext(s -> logRecordService.addLog("received msg:" + s))
                .then()
        ).onTerminateDetach().doOnError(throwable -> logRecordService.addLog("error!")).subscribe();
    }
}
