package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterController.class);

    /**
     * "getSession": {
     *     "info": "根据openid获取基础信息",
     *     "input": {
     *       "openid": "用户的openid"
     *     },
     *     "output": {
     *       "session": "用户的session",
     *       "uid": "用户的uid",
     *       "is_register": "用户是否注册"
     *     }
     *
     * @return API response json
     */
    @GetMapping(value = "/api/session")
    public ApiResponse getSession() {
        return ApiResponse.ok();
    }

    /**
     * "getPhoneCode": {
     *     "info": "根据手机号获取手机验证码",
     *     "input": {
     *       "session": "用户的session",
     *       "uid": "用户的uid",
     *       "phone": "用户的手机号码"
     *     },
     *     "output": {
     *       "is_ok": "发送是否成功",
     *       "message": "错误信息"
     *     }
     *
     * @return API response json
     */
    @GetMapping(value = "/api/verificationCode")
    public ApiResponse getVerificationCode() {
        return ApiResponse.ok();
    }

    /**
     * "register": {
     *     "info": "实名认证",
     *     "input": {
     *       "session": "用户的session",
     *       "uid": "用户的uid",
     *       "phone": "用户的手机号码",
     *       "phone_code": "用户的手机验证码",
     *       "name": "用户名字",
     *       "id": "身份证号码"
     *     },
     *     "output": {
     *       "is_ok": "认证是否成功",
     *       "message": "错误消息"
     *     }
     *
     * @return API response json
     */
    @GetMapping(value = "/api/register")
    public ApiResponse register() {
        return ApiResponse.ok();
    }

    /**
     * "getAuthInfo": {
     *     "info": "获取用户信息",
     *     "input": {
     *       "session": "用户的session",
     *       "uid": "用户的uid"
     *     },
     *     "output": {
     *       "sex": "性别",
     *       "birth": "出生日",
     *       "college": "学校"
     *     }
     *
     * @return API response json
     */
    @GetMapping(value = "/api/userInfo")
    public ApiResponse getUserInfo() {
        return ApiResponse.ok();
    }

    /**
     * "putAuthInfo": {
     *     "info": "修改用户信息",
     *     "input": {
     *       "session": "用户的session",
     *       "uid": "用户的uid",
     *       "sex": "性别",
     *       "birth": "出生日",
     *       "college": "学校"
     *     },
     *     "output": {
     *       "is_ok": "修改是否成功"
     *     }
     *
     * @return API response json
     */
    @PostMapping(value = "/api/userInfo")
    public ApiResponse createUserInfo() {
        return ApiResponse.ok();
    }

    /**
     * "getServiceProxy": {
     *     "info": "获取服务条款",
     *     "input": {
     *     },
     *     "output": {
     *       "context": "服务条款信息"
     *     }
     *   },
     *
     * @return API response json
     */
    @GetMapping(value = "/api/serviceRule")
    public ApiResponse getServiceRule() {
        return ApiResponse.ok();
    }

    /**
     * "feedback": {
     *     "info": "用户反馈",
     *     "input": {
     *       "context": "用户反馈内容"
     *     },
     *     "output:" {
     *       "is_ok": "修改是否成功"
     *     }
     *
     * @return API response json
     */
    @PostMapping(value = "/api/feedback")
    public ApiResponse createFeedback() {
        return ApiResponse.ok();
    }

}
