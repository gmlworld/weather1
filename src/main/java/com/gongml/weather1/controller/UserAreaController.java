package com.gongml.weather1.controller;

import com.gongml.weather1.api.UserAreaService;
import com.gongml.weather1.api.UserService;
import com.gongml.weather1.entity.User;
import com.gongml.weather1.entity.UserArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: weather1
 * @description: 天气
 * @author: Gongml
 * @create: 2018-08-08 13:05
 **/
@Controller
@RequestMapping("/userArea")
public class UserAreaController {
    @Autowired
    private UserAreaService userAreaService;

    @GetMapping("/all")
    @ResponseBody
    public List<UserArea> getAll() {
        return userAreaService.findAll();
    }

    @GetMapping("/add")
    @ResponseBody
    public UserArea addUserArea(long userId, long areaId) {
        UserArea userArea = new UserArea();
        userArea.setUserId(userId);
        userArea.setAreaId(areaId);
        userArea = userAreaService.save(userArea);
        return userArea;
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteUserArea(long id) {
        userAreaService.deleteById(id);
        return "OK";
    }

}