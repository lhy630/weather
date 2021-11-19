package com.alan.weather.service;

import java.util.Optional;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/19 14:21
 * @Version 1.0
 **/
public interface RetryServcie {
    Optional<Integer> retry(String countyId) throws Exception;
}
