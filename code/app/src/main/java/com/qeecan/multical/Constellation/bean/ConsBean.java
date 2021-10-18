package com.qeecan.multical.Constellation.bean;
/*
 * 存放星座显示的类型内容
 * */
public class ConsBean {
    private int consimageId;
    private String consname;


    public ConsBean(int consimageId, String consname) {
        this.consname = consname;
        this.consimageId = consimageId;
    }

    public int getConsimageId() {
        return consimageId;
    }

    public void setConsimageId(int consimageId) {
        this.consimageId = consimageId;
    }

    public String getConsname() {
        return consname;
    }

    public void setConsname(String consname) {
        this.consname = consname;
    }
}
