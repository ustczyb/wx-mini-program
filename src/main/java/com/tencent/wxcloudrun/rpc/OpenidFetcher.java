package com.tencent.wxcloudrun.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.model.common.OpenidRequest;
import com.tencent.wxcloudrun.model.common.OpenidResponse;
import com.tencent.wxcloudrun.service.impl.LoginServiceImpl;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
@Setter
public class OpenidFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenidFetcher.class);

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public OpenidResponse fetchOpenidResponse(OpenidRequest request) throws IOException {

        CloseableHttpResponse httpResponse = null;
        OpenidResponse openidResponse = null;
        try {
            HttpGet httpget = buildRequest(request);
            httpResponse = httpClient.execute(httpget);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseJson = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                LOGGER.info("openid response json: {}", responseJson);
                openidResponse = objectMapper.readValue(responseJson, OpenidResponse.class);
            } else {
                LOGGER.warn("fetch openid failed, response={}", httpResponse);
            }
        } catch (IOException e) {
            LOGGER.error("fetch openid error,", e);
        } catch (URISyntaxException e) {
            LOGGER.error("fetch openid error", e);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
        return openidResponse;
    }

    private HttpGet buildRequest(OpenidRequest request) throws URISyntaxException {
        // 定义请求的参数
        URI uri = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session")
                .setParameter("appid", request.getAppid())
                .setParameter("secret", request.getSecret())
                .setParameter("js_code", request.getJsCode())
                .setParameter("grant_type", request.getGrantType())
                .build();
        // 创建http GET请求
        return new HttpGet(uri);
    }

}
