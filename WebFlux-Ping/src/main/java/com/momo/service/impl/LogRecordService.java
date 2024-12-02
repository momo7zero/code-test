package com.momo.service.impl;

import com.momo.config.Constants;
import com.momo.config.KafkaProducer;
import com.momo.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogRecordService implements ILogRecordService {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public void addLog(String logInfo) {
        log.info("=====ping log:{}", logInfo);
        this.kafkaProducer.send(Constants.KAFKA_TOPIC, logInfo);
    }
}
