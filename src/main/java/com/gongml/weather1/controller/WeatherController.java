package com.gongml.weather1.controller;


import com.gongml.weather1.api.WeatherService;
import com.gongml.weather1.entity.Area;
import com.gongml.weather1.entity.WeatherEntity;
import com.gongml.weather1.entity.WeatherInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: weather1
 * @description: 天气
 * @author: Gongml
 * @create: 2018-08-08 13:05
 **/
@Controller
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/get")
    @ResponseBody
    public WeatherInfo getWeather(String areaName) {
        return getWeatherByIndex(areaName, 0);
    }

    @GetMapping("/getByIndex")
    @ResponseBody
    public WeatherInfo getWeatherByIndex(String areaName, Integer index) {
        Area area = weatherService.findAreaByKeyWord(areaName);
        if (area != null && StringUtils.isNotEmpty(area.getAreaNumber())) {
            return getWeatherInfo(area, index);
        }
        return null;
    }

    @GetMapping("/add")
    @ResponseBody
    public Area addArea(String areaName) {
        Area area = weatherService.addArea(areaName);
        return area;
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<Area> findAll(){
        return weatherService.findAll();
    }

    @GetMapping("/addAndGet")
    @ResponseBody
    public WeatherInfo addAndGetWeather(String areaName) {
        WeatherInfo weather = getWeather(areaName);
        if (weather == null) {
            Area area = addArea(areaName);
            if (area != null && StringUtils.isNotEmpty(area.getAreaNumber())) {
                return getWeatherInfo(area, 0);
            }
        }
        return weather;
    }

    private WeatherInfo getWeatherInfo(Area area, Integer index) {
        WeatherEntity weather = weatherService.getWeather(area.getAreaNumber());
        if (index == null) {
            return weather.getWeatherInfoByIndex(0);
        }
        if (index > 7 || index < 0) {
            return null;
        }
        return weather.getWeatherInfoByIndex(index);
    }

    @GetMapping("/delete")
    @ResponseBody
    public void deleteAreaByName(String areaName) {
        weatherService.deleteByKeyWord(areaName);
    }

    @GetMapping("/deleteById")
    @ResponseBody
    public void deleteAreaById(long id) {
        weatherService.deleteById(id);
    }
}

