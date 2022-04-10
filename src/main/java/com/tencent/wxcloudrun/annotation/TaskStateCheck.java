package com.tencent.wxcloudrun.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yubo.zhang
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface TaskStateCheck {
    String value() default "";
}
