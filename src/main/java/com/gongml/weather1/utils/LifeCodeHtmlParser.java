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
public class LifeCodeHtmlParser extends IHtmlParse {

    private String weatherXpath = "//*[@id=\"livezs\"]";

    @Override
    public List<Map<String, Object>> parseTable(String html) {
        Document doc = Jsoup.parse(html);
        Elements eles = Xsoup.compile(weatherXpath).evaluate(doc).getElements();
        if (eles == null || eles.size() <= 0) {
            throw new UnsupportedOperationException("解析xpath,不允许为空");
        }
        List<Map<String, Object>> tableList = new ArrayList<>();
        Elements elementsByAttributeValue = eles.get(0).getElementsByAttributeValue("class", "clearfix");
        StringBuffer buffer = new StringBuffer();
        for(Element e : elementsByAttributeValue){
            Elements allElements = e.getElementsByTag("li");
            Map<String, Object> rowMap = new HashMap<>();
            for (Element element:allElements){
                Elements em = element.getElementsByTag("em");
                Elements span = element.getElementsByTag("span");
                Elements p = element.getElementsByTag("p");
                if (em!=null && em.size()>0){
                    buffer.setLength(0);
                    if(span!=null && span.size()>0){
                        if("li2 hot".equalsIgnoreCase(element.attr("class"))){
                            Elements star = span.get(0).getElementsByAttributeValue("class", "star");
                            buffer.append(star==null?0:star.size()).append("星").append("\n\r");
                        }else{
                            buffer.append(span.text()).append("\n\r");
                        }
                    }
                    if(p!=null && p.size()>0){
                        buffer.append(p.text()).append("\n\r");
                    }
                    rowMap.put(em.text(),buffer.toString());
                }
            }
            tableList.add(rowMap);
        }
        return tableList;
    }

}
