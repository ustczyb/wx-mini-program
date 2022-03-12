package com.tencent.wxcloudrun.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConvertUtils {

    private ConvertUtils() {}

    public static List<Long> string2idList(String listStr) {
        return Stream.of(listStr.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
