package com.momo.service;

import com.baomidou.mybatisplus.service.IService;
import com.momo.entity.LogRecord;

public interface ILogRecordService extends IService<LogRecord> {
    public void addLog(String type,String log);
}
