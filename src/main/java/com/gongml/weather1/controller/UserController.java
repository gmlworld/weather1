package com.gongml.weather1.controller;

import com.gongml.weather1.api.UserService;
import com.gongml.weather1.dao.UserDao;
import com.gongml.weather1.entity.User;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @ResponseBody
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/add")
    @ResponseBody
    public User addUser(String name, String sckey) {
        User user = new User();
        user.setName(name);
        user.setSckey(sckey);
        User save = userService.save(user);
        return save;
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteUser(long id) {
        userService.deleteById(id);
        return "OK";
    }

}