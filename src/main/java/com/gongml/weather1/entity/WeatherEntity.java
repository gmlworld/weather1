package com.gongml.weather1.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: weather1
 * @description: 天气结果实体类
 * @author: Gongml
 * @create: 2018-08-08 12:38
 **/
@Data
public class WeatherEntity {
    private List<Map<String, Object>> weatherInfoList;
    private List<Map<String, Object>> lifeCodeInfoList;

    public WeatherInfo getWeatherInfoByIndex(int index) {
        WeatherInfo weatherInfo = new WeatherInfo();
        if (weatherInfoList != null && index >= 0 && weatherInfoList.size() > index) {
            weatherInfo.setWeatherInfo(weatherInfoList.get(index));
        }
        if (lifeCodeInfoList != null && index >= 0 && lifeCodeInfoList.size() > index) {
            weatherInfo.setLifeCodeInfo(lifeCodeInfoList.get(index));
        }
        return weatherInfo;
    }
}
