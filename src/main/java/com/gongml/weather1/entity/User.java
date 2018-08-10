package com.gongml.weather1.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: weather1
 * @description: 用户信息
 * @author: Gongml
 * @create: 2018-08-08 17:19
 **/
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @Column(name = "name")
    private String name;
    @Column(name = "sckey")
    private String sckey;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSckey() {
        return sckey;
    }

    public void setSckey(String sckey) {
        this.sckey = sckey;
    }
}
