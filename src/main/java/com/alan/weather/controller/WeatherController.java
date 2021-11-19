package com.alan.weather.controller;

import com.alan.weather.interceptor.RateLimit;
import com.alan.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/17 18:23
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/data/sk")
public class WeatherController {

    @Autowired
    WeatherService weatherService;


    @RateLimit(limitNum = 100.0)
    @GetMapping("/{province}/{city}/{county}")
    public Optional<Integer> getTemperature(@PathVariable("province") String province, @PathVariable("city")String city,
                                            @PathVariable("county") String county) throws Exception {
        //log.info("receive params: province: {},city: {},county: {}" , new String[]{province, city, county} );
        return weatherService.getTemperature(province + city + county);
    }
}
