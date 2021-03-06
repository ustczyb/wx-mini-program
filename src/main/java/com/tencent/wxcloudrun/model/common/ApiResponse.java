package com.tencent.wxcloudrun.model.common;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

    private Integer code;
    private String msg;
    private Object data;

    private ApiResponse(int code, String errorMsg, Object data) {
        this.code = code;
        this.msg = errorMsg;
        this.data = data;
    }

    public static ApiResponse ok() {
        return new ApiResponse(0, "", new HashMap<>());
    }

    public static ApiResponse ok(Object data) {
        return new ApiResponse(0, "", data);
    }

    public static ApiResponse error(int code, String errorMsg) {
        return new ApiResponse(code, errorMsg, new HashMap<>());
    }
}
