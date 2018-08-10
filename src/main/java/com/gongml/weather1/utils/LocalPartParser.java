package com.gongml.weather1.utils;

import com.gongml.weather1.entity.iface.IHtmlParse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LocalPartParser extends IHtmlParse {

    @Override
    public String parseStr(String html) {
        //简单的字符串切割，有需要可以json转换获取批量列表
        String findStr = "{\"ref\":\"";
        String endStr = "~";
        if (StringUtils.isNotBlank(html) && html.contains(findStr)) {
            int startIndex = html.indexOf(findStr)+findStr.length();
            int endIndex = html.indexOf(endStr);
            String retStr = html.substring(startIndex,endIndex);
            return retStr;
        }
        return null;
    }

}
