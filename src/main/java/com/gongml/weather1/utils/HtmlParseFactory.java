package com.gongml.weather1.utils;

import com.gongml.weather1.entity.iface.IHtmlParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: weather1
 * @description: html解析工厂类
 * @author: Gongml
 * @create: 2018-08-08 11:17
 **/
@Component
public class HtmlParseFactory {

    @Autowired
    private LifeCodeHtmlParser lifeCodeHtmlParser;
    @Autowired
    private LocalPartParser localPartParser;
    @Autowired
    private WeatherHtmlParser weatherHtmlParser;

    public IHtmlParse getHtmlParse(int type) {
        IHtmlParse htmlParse = null;
        switch (type) {
            case 1:
                htmlParse = lifeCodeHtmlParser;
                break;
            case 2:
                htmlParse = localPartParser;
                break;
            case 3:
                htmlParse = weatherHtmlParser;
                break;
            default:
                break;
        }
        return htmlParse;
    }
}
