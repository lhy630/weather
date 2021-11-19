package com.alan.weather.interceptor;

import java.lang.annotation.*;

/**
 * @Description 限流注解
 * @Author Orcs_Alan
 * @Date 2021/11/18 17:48
 * @Version 1.0
 **/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RateLimit {

 double limitNum() default 100;
}
