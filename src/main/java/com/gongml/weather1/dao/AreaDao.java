package com.gongml.weather1.dao;

/**
 * @program: weather1
 * @description: area数据库操作
 * @author: Gongml
 * @create: 2018-08-08 10:14
 **/
import com.gongml.weather1.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AreaDao extends JpaRepository<Area, Long> {
    Area findByKeyWord(String keyWord);

    void deleteByKeyWordEquals(String keyWord);

    @Query("select a from Area a where a.ID  in ?1 ")
    List<Area> findByIDIn(Long[] id);
}
