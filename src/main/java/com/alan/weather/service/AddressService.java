package com.alan.weather.service;

import com.alan.weather.entity.City;
import com.alan.weather.entity.County;
import com.alan.weather.entity.Province;

import java.util.List;

/**
 * @Description TODO
 * @Author Orcs_Alan
 * @Date 2021/11/18 11:31
 * @Version 1.0
 **/
public interface AddressService {

    /**
     * 查询所有省信息
     * @return 省列表
     */
    public List<Province> listProvinces();

    /**
     * 查询指定省的城市列表
     * @param provinceId 省ID
     * @return 城市列表
     */
    public List<City> listCitys(String provinceId);

    /**
     * 查询指定城市的市/县列表
     * @param cityId 城市ID
     * @return 市/县列表
     */
    public List<County> listCountys(String cityId);
}
