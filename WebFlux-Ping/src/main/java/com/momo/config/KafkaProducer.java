package com.momo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Random;

@Slf4j
@Component
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String, String> mKafkaTemplate;

    /**
     * 发送消息
     *
     * @param topic topic名称
     * @param key   消息key
     * @param msg   消息内容
     * @return
     */
    public void send(String topic, int partition, String key, String msg) {
        try {
            ListenableFuture<SendResult<String, String>> futureResult = mKafkaTemplate.send(
                    topic, partition, key, msg);
            log.info("Kafka send  topicName: {}, key {}, msg {}", topic, key, msg);
            futureResult.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("Produce: The message was sent successfully: {}", result.toString());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.info("Produce: The messagel failed to be sent:" + ex.getMessage());
                }
            });
        } catch (Throwable e) {
            log.error("Kafka send failed." + e.getMessage(), e);
        }
    }

    public void send(String topic, String msg) {
        send(topic, 1, getUniqueKey(), msg);
    }


    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
