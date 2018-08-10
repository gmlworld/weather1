package com.gongml.weather1.api;

import com.gongml.weather1.entity.Area;
import com.gongml.weather1.entity.WeatherEntity;
import com.gongml.weather1.entity.iface.IHtmlParse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: weather1
 * @description: 天气相关服务
 * @author: Gongml
 * @create: 2018-08-08 10:16
 **/
public interface WeatherService {

    public Area addArea(String keyWord);

    public Area findAreaByKeyWord(String keyWord);

    public void deleteByKeyWord(String keyWord);

    public void deleteById(long id);

    public WeatherEntity getWeather(String areaNumber);

    public List<Area> findAll();

    public List<Area> findAreasByIDIn(Long ...ids);
}
