package com.gongml.weather1.entity.iface;

import java.util.Map;

public interface IHtmlMap<k,v> {
    public Map<k,v> parseMap(String html);
}
