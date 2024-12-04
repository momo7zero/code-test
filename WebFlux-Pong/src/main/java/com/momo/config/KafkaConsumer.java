package com.momo.config;

import com.momo.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka消费者
 */
@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private ILogRecordService logRecordService;

    /***
     *
     * 消费处理者
     * @param records
     */
    @KafkaListener(topics = {"dataware_to_msb_out"})
    public void handler(String records) {
        this.logRecordService.addLog(Constants.LOG_TYPE_PING,records);
    }
}
