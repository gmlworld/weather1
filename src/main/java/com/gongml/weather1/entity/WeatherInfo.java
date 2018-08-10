package com.gongml.weather1.entity;

import lombok.Data;

import java.util.Map;

/**
 * @program: weather1
 * @description: 天气信息
 * @author: Gongml
 * @create: 2018-08-08 12:40
 **/
@Data
public class WeatherInfo {
    Map<String, Object> weatherInfo;
    Map<String, Object> lifeCodeInfo;
}
