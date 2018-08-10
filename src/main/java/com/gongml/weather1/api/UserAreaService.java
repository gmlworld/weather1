package com.gongml.weather1.api;

import com.gongml.weather1.entity.Area;
import com.gongml.weather1.entity.User;
import com.gongml.weather1.entity.UserArea;
import com.gongml.weather1.entity.WeatherEntity;

import java.util.List;

/**
 * @program: weather1
 * @description: 天气相关服务
 * @author: Gongml
 * @create: 2018-08-08 10:16
 **/
public interface UserAreaService {

    public List<UserArea> findAll();

    public UserArea save(UserArea userArea);

    public void deleteById(long id);

    public List<UserArea> findAllByUserId(Long id);
}
