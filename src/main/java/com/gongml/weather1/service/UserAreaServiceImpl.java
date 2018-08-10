package com.gongml.weather1.service;

import com.gongml.weather1.api.UserAreaService;
import com.gongml.weather1.api.UserService;
import com.gongml.weather1.dao.UserAreaDao;
import com.gongml.weather1.dao.UserDao;
import com.gongml.weather1.entity.User;
import com.gongml.weather1.entity.UserArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: weather1
 * @description: 用户信息服务实现
 * @author: Gongml
 * @create: 2018-08-09 08:55
 **/
@Service
public class UserAreaServiceImpl implements UserAreaService {

    @Autowired
    private UserAreaDao userAreaDao;

    @Override
    public List<UserArea> findAll() {
        return userAreaDao.findAll();
    }

    @Override
    public UserArea save(UserArea userArea) {
        return userAreaDao.save(userArea);
    }

    @Override
    public void deleteById(long id) {
        userAreaDao.deleteById(id);
    }

    @Override
    public List<UserArea> findAllByUserId(Long id) {
        return userAreaDao.findAllByUserId(id);
    }

}
