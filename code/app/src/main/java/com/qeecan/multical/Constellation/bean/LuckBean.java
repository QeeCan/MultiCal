package com.qeecan.multical.Constellation.bean;
/*
 * 存放星座运势内容，对接显示信息和网络获取的数据格式
 * */

public class LuckBean {

    /**
     * date : 20210602
     * name : 双鱼座
     * QFriend : 双鱼座
     * color : 灰色
     * datetime : 2021年06月02日
     * health : 75
     * love : 60
     * work : 65
     * money : 80
     * number : 4
     * summary : 也许眼下有一些问题会让你觉得难以面对，非常棘手，甚至会影响到整个人的状态，但是双鱼座也相信，只要熬过了这段时间，解决了眼下的问题，那么就什么都不是事了。
     * all : 80
     * resultcode : 200
     * error_code : 0
     */

    private int date;
    private String name;
    private String QFriend;
    private String color;
    private String datetime;
    private String health;
    private String love;
    private String work;
    private String money;
    private String number;
    private String summary;
    private String all;
    private String resultcode;
    private int error_code;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
