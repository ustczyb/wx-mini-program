package com.tencent.wxcloudrun.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JacksonFactory {

    @Bean
    public ObjectMapper genObjectMapper() {
        return new ObjectMapper();
    }
}
