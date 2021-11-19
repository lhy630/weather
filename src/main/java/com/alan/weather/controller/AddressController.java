package com.alan.weather.controller;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/18 11:40
 * @Version 1.0
 **/

import com.alan.weather.entity.City;
import com.alan.weather.entity.County;
import com.alan.weather.entity.Province;
import com.alan.weather.interceptor.RateLimit;
import com.alan.weather.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/data/city3jdata")
public class AddressController {

    @Autowired
    AddressService addressService;


    @RateLimit(limitNum = 100)
    @GetMapping("/province")
    public List<Province> listProvinces(){
        List<Province> provinceList = addressService.listProvinces();
        //log.info("provinces list: {}" , provinceList);
        return provinceList;
    }

    @GetMapping("city/{city}")
    public List<City> listCitys(@PathVariable("city") String province){
        return addressService.listCitys(province);
    }

    @GetMapping("county/{county}")
    public List<County> listCountys(@PathVariable("county") String county){
        return addressService.listCountys(county);
    }
}
