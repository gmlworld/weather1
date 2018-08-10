package com.gongml.weather1.service;

import com.gongml.weather1.api.UserService;
import com.gongml.weather1.dao.UserDao;
import com.gongml.weather1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @program: weather1
 * @description: 用户信息服务实现
 * @author: Gongml
 * @create: 2018-08-09 08:55
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User save(User user) {
        User save = userDao.save(user);
        return save;
    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findById(long id){
        return  userDao.findById(id);
    }
}
