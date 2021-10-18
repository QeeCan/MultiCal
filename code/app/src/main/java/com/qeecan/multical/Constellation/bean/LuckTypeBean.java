package com.qeecan.multical.Constellation.bean;
/*
* 存放星座运势显示的类型内容
* */

public class LuckTypeBean {

    private String ctitle;
    private String ccontent;

    public LuckTypeBean(String ctitle, String ccontent) {
        this.ctitle = ctitle;
        this.ccontent = ccontent;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

}
