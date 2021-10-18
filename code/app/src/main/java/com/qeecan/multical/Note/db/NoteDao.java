package com.qeecan.multical.Note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qeecan.multical.Note.bean.NoteBean;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    private NoteSQLiteHelper nHelper;
    private SQLiteDatabase db;
    private static final String[] columns = {
            NoteSQLiteHelper.ID,
            NoteSQLiteHelper.TITLE,
            NoteSQLiteHelper.CONTENT,
            NoteSQLiteHelper.TAG,
            NoteSQLiteHelper.TIME
    };


    public NoteDao() {}

    public NoteDao(Context context) {
        nHelper=new NoteSQLiteHelper(context);
    }

    //打开数据库进行操作
    public void open(){
        db = nHelper.getWritableDatabase();
    }

    //关闭数据库操作
    public void close(){
        nHelper.close();
    }

    /*
    *
    * 增删改查的方法
    *
    * */

    //增加方法
    public NoteBean addNote(NoteBean note){
            ContentValues values = new ContentValues();
            values.put(NoteSQLiteHelper.TITLE,note.getTitle());
            values.put(NoteSQLiteHelper.CONTENT, note.getContent());
            values.put(NoteSQLiteHelper.TAG, note.getTag());
            values.put(NoteSQLiteHelper.TIME, note.getTime());
            long insertId = db.insert(NoteSQLiteHelper.TABLE_NAME, null, values);
            note.setId(insertId);
        return note;
    }

  //  通过自增长id定位,得到具体的某条记录
    public NoteBean getNote(long id){

            //游标定位
            Cursor cursor = db.query(NoteSQLiteHelper.TABLE_NAME, columns, NoteSQLiteHelper.ID+ "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) cursor.moveToFirst();
            NoteBean e = new NoteBean(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return e;
    }

    //得到全部，使用游标遍历
    public List<NoteBean> getAllNotes(){
        Cursor cursor = db.query(NoteSQLiteHelper.TABLE_NAME,columns,null,null,null, null, null);

        List<NoteBean> notes = new ArrayList<>();
        //使用前判断其是否有内容
        if(cursor.getCount() > 0){
            //循环遍历Cursor对象，获取下一行是否有内容，如果有，进行获取
            while(cursor.moveToNext()){
                NoteBean note = new NoteBean();
                //获取cursor对象索引为0的数据
                note.setId(cursor.getInt(cursor.getColumnIndex(NoteSQLiteHelper.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteSQLiteHelper.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteSQLiteHelper.CONTENT)));
                note.setTag(cursor.getString(cursor.getColumnIndex(NoteSQLiteHelper.TAG)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteSQLiteHelper.TIME)));
                notes.add(note);
            }
        }
        return notes;
    }

    //更新
    public int updateNote(NoteBean note) {
        ContentValues values = new ContentValues();
        values.put(NoteSQLiteHelper.TITLE, note.getTitle());
        values.put(NoteSQLiteHelper.CONTENT, note.getContent());
        values.put(NoteSQLiteHelper.TAG, note.getTag());
        values.put(NoteSQLiteHelper.TIME, note.getTime());

        // 更新行
        return db.update(NoteSQLiteHelper.TABLE_NAME, values,
                NoteSQLiteHelper.ID + "=?",new String[] { String.valueOf(note.getId())});
    }

    //删除
    public void removeNote(NoteBean note) {
        //根据id删除
        db.delete(NoteSQLiteHelper.TABLE_NAME, NoteSQLiteHelper.ID + "=" + note.getId(), null);
    }

}
