package com.gongml.weather1.utils;

import com.gongml.weather1.entity.iface.IHtmlParse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class WeatherHtmlParser extends IHtmlParse {

    private String weatherXpath = "//*[@id=\"7d\"]/ul";

    @Override
    public List<Map<String, Object>> parseTable(String html) {
        Document doc = Jsoup.parse(html);
        Elements eles = Xsoup.compile(weatherXpath).evaluate(doc).getElements();
        if (eles == null || eles.size() <= 0) {
            throw new UnsupportedOperationException("解析xpath,不允许为空");
        }
        List<Map<String, Object>> tableList = new ArrayList<>();
        for (Element element : eles.get(0).getAllElements()) {
            if ("li".equalsIgnoreCase(element.tagName())) {
                Map<String, Object> rowMap = new HashMap<>();
                Elements allElements = element.getAllElements();
                for (Element e : allElements) {
                    parseWeather(rowMap, e);
                }
                tableList.add(rowMap);
            }
        }
        return tableList;
    }

    private void parseWeather(Map<String, Object> rowMap, Element e) {
        if ("h1".equalsIgnoreCase(e.tagName())) {
            rowMap.put("date", e.text());
        } else if ("p".equalsIgnoreCase(e.tagName()) && e.hasAttr("class")) {
            String aClass = e.attr("class");
            switch (aClass) {
                case "wea":
                    rowMap.put("weather", e.text());
                    break;
                case "tem":
                    rowMap.put("tem", e.text());
                    break;
                case "win":
                    Elements spans = e.getElementsByTag("span");
                    StringBuffer buffer = new StringBuffer();
                    for (Element span : spans) {
                        if (span.hasAttr("title")) {
                            buffer.append(span.attr("title")).append(',');
                        }
                    }
                    buffer.append(e.text());
                    rowMap.put("wind", buffer.toString());
                    break;
                default:
                    break;
            }
        }
    }
}
