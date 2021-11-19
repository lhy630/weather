package com.alan.weather.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @Description 天气信息服务
 * @Author Orcs_Alan
 * @Date 2021/11/17 18:22
 * @Version 1.0
 **/
@Service
public interface WeatherService {

    /**
     * 查询指定市/县的气温
     * @param countyId
     * @return 气温
     */
    Optional<Integer> getTemperature(String countyId) throws Exception;
}
