package com.gongml.weather1.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @program: weather1
 * @description: 公共工具类
 * @author: Gongml
 * @create: 2018-08-08 10:56
 **/
@Component
public class CommonUtils {
    private static final String weatherAreaUrl = "http://toy1.weather.com.cn/search?cityname=";

    private static final String weatherUrl = "http://www.weather.com.cn/weather/";

    public String getWeatherAreaUrl(String areaName) {
        String encode = encode(areaName, "UTF-8");
        return weatherAreaUrl + encode;
    }

    public String getWeatherUrl(String areaCode) {
        return weatherUrl + areaCode + ".shtml";
    }

    public String encode(String str, String codeType) {
        String encode = "";
        try {
            encode = URLEncoder.encode(str, codeType);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("encode执行错误:" + e.getMessage());
        }
        return encode;
    }
}
