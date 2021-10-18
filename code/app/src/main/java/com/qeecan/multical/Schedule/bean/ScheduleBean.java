package com.qeecan.multical.Schedule.bean;

import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleBean {
    private long id;
    private String content;
    private Calendar time;


    public ScheduleBean(String content, String time) {
        this.content = content;
        setClock(time);
    }

    public ScheduleBean() {
        this.time = Calendar.getInstance();
    }

    public int getYear() {
        return time.get(Calendar.YEAR);
    }

    public int getMonth() {
        return time.get(Calendar.MONTH);
    }

    public int getDay() {
        return time.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return time.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return time.get(Calendar.MINUTE);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getClock() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(time.getTime());
    }

    public void setClock(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date temp = simpleDateFormat.parse(format);
            Log.d("setClock success", "" + temp);
            time = Calendar.getInstance();
            time.setTime(temp);
        } catch (ParseException e) {

        }
    }
}
