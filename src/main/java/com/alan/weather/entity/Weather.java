package com.alan.weather.entity;

import lombok.Data;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/17 19:07
 * @Version 1.0
 **/
@Data
public class Weather {
    String cityId;
    String city;
    String temp;
    String wd;
    String ws;
    String sd;
    String ap;
    String njd;
    String wse;
    String time;
    String sm;
    String isRadar;
    String radar;
}
