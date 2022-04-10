package com.tencent.wxcloudrun.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

/**
 * @author yubo.zhang
 */
@Component
public class AopFactory {

    @Bean
    public ParameterNameDiscoverer genNameDiscoverer() {
        return new DefaultParameterNameDiscoverer();
    }
}
