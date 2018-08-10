package com.gongml.weather1.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: weather1
 * @description: 地区实体类
 * @author: Gongml
 * @create: 2018-08-07 13:36
 **/
@Entity
@Table(name = "area")
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @Column(name = "key_word")
    private String keyWord;
    @Column(name = "area_number")
    private String areaNumber;


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }
}
