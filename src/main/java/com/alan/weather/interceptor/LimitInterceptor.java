package com.alan.weather.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 单机版限流实现（分布式环境可采redis分布式锁、redission实现）
 * @Author Orcs_Alan
 * @Date 2021/11/18 17:47
 * @Version 1.0
 **/
@Slf4j
@Aspect
@Configuration
public class LimitInterceptor {

    private RateLimiter rateLimiter;
    private static Map<String, RateLimiter> limitMap = new HashMap<>(20);
    //private ConcurrentHashMap<String, RateLimiter> limitMap = new ConcurrentHashMap<>(20);

    @Pointcut("@annotation(com.alan.weather.interceptor.RateLimit)")
    public void rateLimit() {

    }

    @Around("rateLimit()")
    public synchronized Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        if (limitMap.containsKey(methodName)) {
            rateLimiter = limitMap.get(methodName);
        } else {
            RateLimit rateLimit = method.getAnnotation(RateLimit.class);
            double limitNum = rateLimit.limitNum();
            log.info("methodName: {}, limitNum : {}", methodName, limitNum);
            rateLimiter = RateLimiter.create(limitNum);
            limitMap.put(methodName, rateLimiter);
        }

        Object result;
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            log.info("The system is busy, please try again later ...");
            return null;
            //throw new RuntimeException("The system is busy, please try again later ...");
        }
    }
}

