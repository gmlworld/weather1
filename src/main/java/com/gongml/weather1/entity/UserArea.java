package com.gongml.weather1.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: weather1
 * @description: 用户天气关系实体
 * @author: Gongml
 * @create: 2018-08-09 08:41
 **/
@Entity
@Table(name = "usr_area")
public class UserArea implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "area_id")
    private Long areaId;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

}
