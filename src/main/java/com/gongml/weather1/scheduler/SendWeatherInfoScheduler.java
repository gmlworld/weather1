package com.gongml.weather1.scheduler;

import com.gongml.weather1.api.UserAreaService;
import com.gongml.weather1.api.UserService;
import com.gongml.weather1.api.WeatherService;
import com.gongml.weather1.entity.*;
import com.gongml.weather1.utils.WeatherRequestUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: weather1
 * @description: 天气预报发送调度
 * @author: Gongml
 * @create: 2018-08-08 17:42
 **/
@Component
public class SendWeatherInfoScheduler {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAreaService userAreaService;
    @Autowired
    private WeatherRequestUtil weatherRequestUtil;

    //    @Scheduled(cron = "0/30 * *  * * ? ") //每30秒一次
//    @Scheduled(cron = "0 15 7 * * ? ") //每天7.15
    public void aTask() {
        List<User> users = userService.findAll();
        for (User user : users) {
            long userID = user.getID();
            String sckey = user.getSckey();
            List<Long> ids = getAreasId(userID);
            if (CollectionUtils.isEmpty(ids)) {
                continue;
            }
            List<Area> areas = weatherService.findAreasByIDIn(ids.toArray(new Long[ids.size()]));
            if (CollectionUtils.isEmpty(areas)) {
                continue;
            }
            Area area = areas.get(0);
            WeatherEntity weather = weatherService.getWeather(area.getAreaNumber());
            WeatherInfo weatherInfoByIndex = weather.getWeatherInfoByIndex(0);
            StringBuffer buffer = getWeatherBuffer(weatherInfoByIndex);
            weatherRequestUtil.sendInfoToWx(sckey, "天气预报(" + area.getKeyWord() + ")", buffer.toString());
        }
    }

    /**
     * @Description: 早上7.15提醒带伞
     * @Param: []
     * @return: void
     * @Author: Gongml
     * @Date: 2018-08-09
     */
    @Scheduled(cron = "0 15 7 * * ? ")
    public void task1() {
        User user = userService.findById(1);
        long userID = user.getID();
        String sckey = user.getSckey();
        List<Long> ids = getAreasId(userID);
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<Area> areas = weatherService.findAreasByIDIn(ids.toArray(new Long[ids.size()]));
        Area area = areas.get(0);
        WeatherEntity weather = weatherService.getWeather(area.getAreaNumber());
        WeatherInfo weatherInfoByIndex = weather.getWeatherInfoByIndex(0);
        Map<String, Object> weatherInfo = weatherInfoByIndex.getWeatherInfo();
        StringBuffer buffer = getWeatherBuffer(weatherInfoByIndex);
        String weather1 = MapUtils.getString(weatherInfo, "weather", "");
        if (weather1.contains("雨") || weather1.contains("雪")) {
            weatherRequestUtil.sendInfoToWx(sckey, "今天有雨，记得带伞(" + area.getKeyWord() + ")", buffer.toString());
        }
    }

    /**
     * @Description: 晚上 9.30提醒第二天天气
     * @Param: []
     * @return: void
     * @Author: Gongml
     * @Date: 2018-08-09
     */
    @Scheduled(cron = "0 30 21 * * ? ")
    public void task2() {
        User user = userService.findById(1);
        long userID = user.getID();
        String sckey = user.getSckey();
        List<Long> ids = getAreasId(userID);
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<Area> areas = weatherService.findAreasByIDIn(ids.toArray(new Long[ids.size()]));
        Area area = areas.get(0);
        WeatherEntity weather = weatherService.getWeather(area.getAreaNumber());
        WeatherInfo weatherInfoByIndex = weather.getWeatherInfoByIndex(1);
        Map<String, Object> weatherInfoMap = weatherInfoByIndex.getWeatherInfo();
        StringBuffer buffer = getWeatherBuffer(weatherInfoByIndex);
        String weather1 = MapUtils.getString(weatherInfoMap, "weather", "");
        if (weather1.contains("雨") || weather1.contains("雪")) {
            weatherRequestUtil.sendInfoToWx(sckey, "明天带伞出门哦(" + area.getKeyWord() + ")", buffer.toString());
        } else {
            weatherRequestUtil.sendInfoToWx(sckey, "明天天气推送(" + area.getKeyWord() + ")", buffer.toString());
        }
    }

    private StringBuffer getWeatherBuffer(WeatherInfo weatherInfo) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("天气情况：\n\r\n\r");
        formatWeather(weatherInfo.getWeatherInfo(), buffer);
        formatWeather(weatherInfo.getLifeCodeInfo(), buffer);
        buffer.append(new Date());
        return buffer;
    }

    private void formatWeather(Map<String, Object> weatherInfo, StringBuffer buffer) {
        for (String key : weatherInfo.keySet()) {
            String value = MapUtils.getString(weatherInfo, key, "");
            buffer.append(key).append(":\n\r");
            if (key.equals("wind")) {
                value = value.replaceAll("<", "小于");
                value = value.replaceAll(">", "大于");
            }
            buffer.append(value).append("\n\r");
        }
    }

    private List<Long> getAreasId(long userID) {
        List<UserArea> userAreas = userAreaService.findAllByUserId(userID);
        if (userAreas == null) {
            return null;
        }
        List<Long> ids = new ArrayList<>();
        for (UserArea userArea : userAreas) {
            Long areaId = userArea.getAreaId();
            ids.add(areaId);
        }
        return ids;
    }
}
