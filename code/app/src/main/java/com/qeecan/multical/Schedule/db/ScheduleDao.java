package com.qeecan.multical.Schedule.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qeecan.multical.Schedule.bean.ScheduleBean;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private SQLiteOpenHelper sHelper;
    private SQLiteDatabase db;
    
    private static final String[] columns = {
            ScheduleSQLiteHelper.ID,
            ScheduleSQLiteHelper.CONTENT,
            ScheduleSQLiteHelper.TIME,
    };
    public ScheduleDao() {}

    public ScheduleDao(Context context) {
        sHelper=new ScheduleSQLiteHelper(context);
    }
    public void open(){
        db = sHelper.getWritableDatabase();
    }

    public void close(){
        sHelper.close();
    }

    public ScheduleBean addSchedule(ScheduleBean schedule){
        //add a ScheduleBean object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScheduleSQLiteHelper.CONTENT, schedule.getContent());
        contentValues.put(ScheduleSQLiteHelper.TIME, schedule.getClock());
        long insertId = db.insert(ScheduleSQLiteHelper.TABLE_NAME, null, contentValues);
        schedule.setId(insertId);
        return schedule;
    }

    public ScheduleBean getSchedule(long id){
        //get a ScheduleBean from database using cursor index
        Cursor cursor = db.query(ScheduleSQLiteHelper.TABLE_NAME,columns,ScheduleSQLiteHelper.ID + "=?",
                new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null) cursor.moveToFirst();
        ScheduleBean e = new ScheduleBean(cursor.getString(1),cursor.getString(2));
        return e;
    }

    public List<ScheduleBean> getAllSchedule(){
        Cursor cursor = db.query(ScheduleSQLiteHelper.TABLE_NAME,columns,null,null,null, null, null);

        List<ScheduleBean> ScheduleBeans = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                ScheduleBean ScheduleBean = new ScheduleBean();
                ScheduleBean.setId(cursor.getLong(cursor.getColumnIndex(ScheduleSQLiteHelper.ID)));
                ScheduleBean.setContent(cursor.getString(cursor.getColumnIndex(ScheduleSQLiteHelper.CONTENT)));
                ScheduleBean.setClock(cursor.getString(cursor.getColumnIndex(ScheduleSQLiteHelper.TIME)));
                ScheduleBeans.add(ScheduleBean);
            }
        }
        return ScheduleBeans;
    }

    public int updateSchedule(ScheduleBean ScheduleBean) {
        //update the info of an existing ScheduleBean
        ContentValues values = new ContentValues();
        values.put(ScheduleSQLiteHelper.CONTENT, ScheduleBean.getContent());
        values.put(ScheduleSQLiteHelper.TIME, ScheduleBean.getClock());
        // updating row
        return db.update(ScheduleSQLiteHelper.TABLE_NAME, values,
                ScheduleSQLiteHelper.ID + "=?",new String[] { String.valueOf(ScheduleBean.getId())});
    }

    public void removeSchedule(ScheduleBean ScheduleBean) {
        //remove a ScheduleBean according to ID value
        db.delete(ScheduleSQLiteHelper.TABLE_NAME, ScheduleSQLiteHelper.ID + "=" + ScheduleBean.getId(), null);
    }
}
