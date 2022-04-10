package com.tencent.wxcloudrun.advice;

import com.tencent.wxcloudrun.annotation.TaskStateCheck;
import com.tencent.wxcloudrun.dao.TaskMapper;
import com.tencent.wxcloudrun.enums.TaskStateEnum;
import com.tencent.wxcloudrun.model.DO.Task;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author yubo.zhang
 */
@Component
@Aspect
public class TaskStateCheckAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskStateCheckAspect.class);

    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;

    @Autowired
    private TaskMapper taskMapper;

    private final Map<String, Method> nameMethodMap = new HashMap<>();

    @Around("@annotation(taskStateCheck)")
    public Object metric(ProceedingJoinPoint joinPoint, TaskStateCheck taskStateCheck) throws Throwable {
        Long taskId = getTaskId(joinPoint);
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if (!Objects.equals(task.getState(), TaskStateEnum.ENDED.getCode())) {
            return joinPoint.proceed();
        } else {
            LOGGER.warn("task {} has been ended.", taskId);
            return -1;
        }
    }

    private Method getMethodByClassAndName(String classType, String methodName, Class<?>[] methodArgTypes) throws ClassNotFoundException, NoSuchMethodException {
        String key = classType + "_" + methodName;
        if (nameMethodMap.get(key) == null) {
            Method method = Class.forName(classType).getMethod(methodName, methodArgTypes);
            nameMethodMap.put(key, method);
        }
        return nameMethodMap.get(key);
    }

    private Long getTaskId(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 参数值
        Object[] args = joinPoint.getArgs();
        Class<?>[] methodArgTypes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                // 获取的是封装类型而不是基础类型
                methodArgTypes[k] = args[k].getClass();
            }
        }
        // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = getMethodByClassAndName(classType, methodName, methodArgTypes);
        // 参数名
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            if (StringUtils.equals(parameterNames[i], "taskId")) {
                return (Long) args[i];
            }
        }
        return null;
    }
}
