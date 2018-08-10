package com.gongml.weather1.dao;

import com.gongml.weather1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @program: weather1
 * @description: 用户数据库操作
 * @author: Gongml
 * @create: 2018-08-08 17:23
 **/
public interface UserDao extends JpaRepository<User, Long> {
    User findById(long id);
}
