package com.qeecan.multical.Account.bean;


/*
* 表示收入或支出的具体类型
* */
public class TypeBean {
    int id;
    String typename;   //类型名称
    int cimageId;    //被选中图片id
    int kind;     //支出0 收入1

    public TypeBean(String typename,  int cimageId ,int kind) {
        this.typename = typename;
        this.cimageId = cimageId;
        this.kind=kind;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getCimageId() {
        return cimageId;
    }

    public void setCimageId(int cimageId) {
        this.cimageId = cimageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
