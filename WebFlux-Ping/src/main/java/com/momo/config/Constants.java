package com.momo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String KAFKA_TOPIC = "dataware_to_msb_out";
    @Value("${server.port}")
    public Integer port;

    public Integer getPort() {
        return port;
    }
}
