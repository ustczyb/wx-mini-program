package com.tencent.wxcloudrun.rpc;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HttpClientFactory {

    @Bean
    public CloseableHttpClient genHttpClient() {
        return HttpClients.createDefault();
    }
}
