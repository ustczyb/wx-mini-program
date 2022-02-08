package com.tencent.wxcloudrun.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.model.common.OpenidRequest;
import com.tencent.wxcloudrun.model.common.OpenidResponse;
import com.tencent.wxcloudrun.rpc.OpenidFetcher;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class OpenidFetcherTest {

    private OpenidFetcher fetcher;

    @Before
    public void init() {

        fetcher = new OpenidFetcher();
        fetcher.setHttpClient(HttpClients.createDefault());
        fetcher.setObjectMapper(new ObjectMapper());

    }

    @Test
    public void test() throws IOException {
        OpenidRequest request = OpenidRequest.builder()
                .appid("wx2e3ceb48ffdf7e40")
                .secret("d01eb239d198275645d4a3ba506a3ebb")
                .jsCode("001IMFFa1GPuCC07wIGa1sZVaM1IMFF0")
                .grantType("authorization_code")
                .build();
        OpenidResponse response = fetcher.fetchOpenidResponse(request);
        System.out.println(response);
    }
}
