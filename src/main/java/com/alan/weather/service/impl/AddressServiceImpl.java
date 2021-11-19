package com.alan.weather.service.impl;

import com.alan.weather.entity.City;
import com.alan.weather.entity.County;
import com.alan.weather.entity.Province;
import com.alan.weather.service.AddressService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/18 11:39
 * @Version 1.0
 **/
@Repository
public class AddressServiceImpl implements AddressService {

    @Value("${addr.provinces:{}}")
    private String provinces;

    @Value("${addr.citys:{}}")
    private String citys;

    @Value("${addr.countys:{}}")
    private String countys;

    /**
     * 查询所有省信息
     *
     * @return 省列表
     */
    @Override
    public List<Province> listProvinces() {
        JSONObject jsonObject = JSONObject.parseObject(this.provinces);

        List<Province> provinceList = new ArrayList<>();
        jsonObject.forEach((k, v) -> {
            Province province = new Province();
            province.setName((String)v);
            province.setId(k);
            provinceList.add(province);
        });
        return provinceList;
    }

    /**
     * 查询指定省的城市列表
     *
     * @param provinceId 省ID
     * @return 城市列表
     */
    @Override
    public List<City> listCitys(String provinceId) {
        JSONObject jsonObject = JSONObject.parseObject(this.citys);
        List<City> cityList = new ArrayList<>();
        jsonObject.forEach((k, v) -> {
            City city = new City();
            city.setName((String)v);
            city.setId(k);
            cityList.add(city);
        });
        return cityList;
    }

    /**
     * 查询指定城市的市/县列表
     *
     * @param countyId 城市ID
     * @return 市/县列表
     */
    @Override
    public List<County> listCountys(String countyId) {
        JSONObject jsonObject = JSONObject.parseObject(this.countys);
        List<County> countyList = new ArrayList<>();
        jsonObject.forEach((k, v) -> {
            County county = new County();
            county.setName((String)v);
            county.setId(k);
            countyList.add(county);
        });
        return countyList;
    }
}
