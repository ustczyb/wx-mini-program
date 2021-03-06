package com.tencent.wxcloudrun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
@MapperScan(basePackages = {"com.tencent.wxcloudrun.dao"})
public class WxCloudRunApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxCloudRunApplication.class, args);
    }
}
