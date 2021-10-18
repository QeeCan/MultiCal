package com.qeecan.multical.Account.bean;

public class AccountBean {
    long id;
    String typename;
    float money;
    String remark;
    String time;
    int kind;


    public AccountBean() {
    }

    public AccountBean(String typename, float money, String remark, String time, int kind) {
        this.typename = typename;
        this.money = money;
        this.remark = remark;
        this.time = time;
        this.kind = kind;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
