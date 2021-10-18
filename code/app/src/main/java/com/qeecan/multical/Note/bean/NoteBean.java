package com.qeecan.multical.Note.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class NoteBean {
    private long id;
    private String title;
    private String content;
    private String time;
    private String tag;


    public NoteBean() {
    }

    public NoteBean(String title, String content, String tag, String time) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.time = time;
    }

//    protected NoteBean(Parcel in) {
//        id = in.readLong();
//        title = in.readString();
//        content = in.readString();
//        time = in.readString();
//        tag = in.readString();
//    }
//
//    public static final Creator<NoteBean> CREATOR = new Creator<NoteBean>() {
//        @Override
//        public NoteBean createFromParcel(Parcel in) {
//            return new NoteBean(in);
//        }
//
//        @Override
//        public NoteBean[] newArray(int size) {
//            return new NoteBean[size];
//        }
//    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return title + "\n" + content + "\n"+ tag + "\n" + time.substring(5, 16) + " " + id;
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(id);
//        dest.writeString(title);
//        dest.writeString(content);
//        dest.writeString(time);
//        dest.writeString(tag);
//    }
}
