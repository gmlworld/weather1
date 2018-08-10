package com.gongml.weather1.dao;

import com.gongml.weather1.entity.UserArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: weather1
 * @description: 用户地区关联表
 * @author: Gongml
 * @create: 2018-08-09 08:51
 **/
public interface UserAreaDao extends JpaRepository<UserArea, Long> {
    List<UserArea> findAllByUserId(Long userId);
}
