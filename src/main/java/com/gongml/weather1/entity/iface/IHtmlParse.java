package com.gongml.weather1.entity.iface;

import java.util.List;
import java.util.Map;

public abstract class IHtmlParse implements IHtmlTable, IHtmlMap, IHtmlStr, IHtmlListStr {
    @Override
    public Map parseMap(String html) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String parseStr(String html) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<String, Object>> parseTable(String html) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> parseListStr(String html) {
        throw new UnsupportedOperationException();
    }
}
