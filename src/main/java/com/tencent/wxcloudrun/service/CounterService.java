package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.counter.Counter;

import java.util.Optional;

public interface CounterService {

    Optional<Counter> getCounter(Integer id);

    void upsertCount(Counter counter);

    void clearCount(Integer id);
}
