package com.momo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("test.log_record")
public class LogRecord {
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * logType ping/pong
     */
    @TableField("log_type")
    private String logType;

    @TableField("log_info")
    private String logInfo;

    @TableField("create_time")
    private Date createTime;
}
