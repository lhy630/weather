package com.alan.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * 因时间关系，以下几点没有完成：
 * 1、没有对异常、请求、返回进行封装
 * 2、没有对相关参数进行校验
 * 3、注解不全
 * 见谅！
 */
@SpringBootApplication
@EnableRetry
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}
