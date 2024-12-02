package com.momo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.momo.config.Constants;
import com.momo.entity.LogRecord;
import com.momo.mapper.LogRecordMapper;
import com.momo.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class LogRecordServiceImpl extends ServiceImpl<LogRecordMapper, LogRecord> implements ILogRecordService {

    @Override
    public void addLog(String type, String logInfo) {
        log.info("====={}-log:{}", type, logInfo);
        LogRecord l = new LogRecord();
        l.setId(UUID.randomUUID().toString());
        l.setCreateTime(new Date());
        l.setLogType(type);
        l.setLogInfo(logInfo);
        this.baseMapper.insert(l);
    }
}
