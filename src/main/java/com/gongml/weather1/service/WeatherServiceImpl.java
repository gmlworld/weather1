package com.gongml.weather1.service;

import com.gongml.weather1.api.WeatherService;
import com.gongml.weather1.dao.AreaDao;
import com.gongml.weather1.entity.Area;
import com.gongml.weather1.entity.WeatherEntity;
import com.gongml.weather1.entity.iface.IHtmlParse;
import com.gongml.weather1.utils.HtmlParseFactory;
import com.gongml.weather1.utils.WeatherRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: weather1
 * @description: 天气相关服务
 * @author: Gongml
 * @create: 2018-08-08 10:17
 **/
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private WeatherRequestUtil weatherRequestUtil;
    @Autowired
    private HtmlParseFactory htmlParseFactory;

    @Override
    public Area addArea(String keyWord) {
        String htmlByAreaName = weatherRequestUtil.getHtmlByAreaName(keyWord);
        if (StringUtils.isNotEmpty(htmlByAreaName)) {
            IHtmlParse htmlParse = htmlParseFactory.getHtmlParse(2);
            String s = htmlParse.parseStr(htmlByAreaName);
            Area area = new Area();
            area.setKeyWord(keyWord);
            area.setAreaNumber(s);
            Area save = areaDao.save(area);
            return save;
        }
        return null;
    }

    @Override
    public Area findAreaByKeyWord(String keyWord) {
        return areaDao.findByKeyWord(keyWord);
    }

    @Override
    public void deleteByKeyWord(String keyWord) {
        areaDao.deleteByKeyWordEquals(keyWord);
    }

    @Override
    public void deleteById(long id) {
        areaDao.deleteById(id);
    }

    @Override
    public WeatherEntity getWeather(String areaNumber) {
        String htmlByAreaNumber = weatherRequestUtil.getHtmlByAreaNumber(areaNumber);
        WeatherEntity weatherEntity = new WeatherEntity();
        if (StringUtils.isNotEmpty(htmlByAreaNumber)) {
            IHtmlParse htmlParse = htmlParseFactory.getHtmlParse(1);
            List<Map<String, Object>> lifeCodeInfoList = htmlParse.parseTable(htmlByAreaNumber);
            htmlParse = htmlParseFactory.getHtmlParse(3);
            List<Map<String, Object>> weatherInfoList = htmlParse.parseTable(htmlByAreaNumber);
            weatherEntity.setLifeCodeInfoList(lifeCodeInfoList);
            weatherEntity.setWeatherInfoList(weatherInfoList);
        }
        return weatherEntity;
    }

    @Override
    public List<Area> findAll() {
        return areaDao.findAll();
    }

    @Override
    public List<Area> findAreasByIDIn(Long ...ids){
        if(ids.length<=0){
            return new ArrayList<>();
        }
        return areaDao.findByIDIn(ids);
    }
}
