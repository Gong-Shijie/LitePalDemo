package com.example.litepaldemo;

import org.litepal.crud.DataSupport;

//此时相当于数据库里的表
public class User extends DataSupport {
    private int id;
    private String name;
    private  int age;
    private String info;
    private String upclass="升级！";

    public String getUpclass() {
        return upclass;
    }

    public void setUpclass(String upclass) {
        this.upclass = upclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
