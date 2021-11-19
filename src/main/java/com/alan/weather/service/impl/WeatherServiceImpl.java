package com.alan.weather.service.impl;

import com.alan.weather.entity.Weather;
import com.alan.weather.service.WeatherService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/18 11:39
 * @Version 1.0
 **/
@Slf4j
@Repository
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.info:{}}")
    private String weatherInfo;
    /**
     * 查询指定市/县的气温
     *
     * @param countyId
     * @return 气温
     */

    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 1, maxDelay = 10000))
    @Override
    public Optional<Integer> getTemperature(String countyId) throws Exception {
        //模拟重试
        if(!StringUtils.hasText(countyId) || countyId.length() != 9){
            log.info("retrying...");
            throw new Exception("invalid parameter");
        }
        Weather weather = JSONObject.parseObject(this.weatherInfo, Weather.class);
        //log.info(weather.getCity() + " temp is : {}", weather.getTemp() );
        return Optional.of(Double.valueOf(weather.getTemp()).intValue());
    }

    @Recover
    public Optional<Integer> recover(Exception e) {
        System.out.println("service recovered...");
        return Optional.of(66);
    }
}
