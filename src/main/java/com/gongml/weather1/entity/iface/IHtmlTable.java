package com.gongml.weather1.entity.iface;

import java.util.List;
import java.util.Map;

public interface IHtmlTable {
    public List<Map<String,Object>> parseTable(String html);
}
