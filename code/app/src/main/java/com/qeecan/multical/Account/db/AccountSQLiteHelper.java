package com.qeecan.multical.Account.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AccountSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "account";
    public static final String TYPENAME = "typename";
    public static final String ID = "_id";
    public static final String REMARK = "remark";
    public static final String MONEY = "money";
    public static final String TIME = "time";
    public static final String KIND = "kind";
    public AccountSQLiteHelper(@Nullable Context context) {
        super(context, "account", null, 1);
    }



    /*
     * 数据库初创建时调用该方法
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPENAME + " TEXT NOT NULL,"
                + REMARK + " TEXT,"
                + MONEY + " FLOAT NOT NULL,"
                + KIND + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL)"

        );
    }


    /*
     * 数据库初版本迭代时可以回调此方法
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }





//    private void insertType(SQLiteDatabase db) {
//        String sql = "insert into atype(typename,imageId,cImageId,kind) values(?,?,?,?)";
//        db.execSQL(sql, new Object[]{"其他", R.drawable.o_qt, R.drawable.o_qt_c, 0});
//        db.execSQL(sql, new Object[]{"餐饮", R.drawable.o_cy, R.drawable.o_cy_c, 0});
//        db.execSQL(sql, new Object[]{"服饰", R.drawable.o_fs, R.drawable.o_fs_c, 0});
//        db.execSQL(sql, new Object[]{"交通", R.drawable.o_jt, R.drawable.o_jt_c, 0});
//        db.execSQL(sql, new Object[]{"日用品", R.drawable.o_ryp, R.drawable.o_ryp_c, 0});
//        db.execSQL(sql, new Object[]{"零食", R.drawable.o_ls, R.drawable.o_ls_c, 0});
//        db.execSQL(sql, new Object[]{"娱乐", R.drawable.o_w, R.drawable.o_w_c, 0});
//        db.execSQL(sql, new Object[]{"学习", R.drawable.o_xx, R.drawable.o_xx_c, 0});
//        db.execSQL(sql, new Object[]{"烟酒", R.drawable.o_yj, R.drawable.o_yj_c, 0});
//        db.execSQL(sql, new Object[]{"医疗", R.drawable.o_yl, R.drawable.o_yl_c, 0});
//        db.execSQL(sql, new Object[]{"住宅", R.drawable.o_zz, R.drawable.o_zz_c, 0});
//        db.execSQL(sql, new Object[]{"通讯", R.drawable.o_tx, R.drawable.o_tx_c, 0});
//
//        db.execSQL(sql, new Object[]{"其他", R.drawable.i_qt, R.drawable.i_qt_c, 1});
//        db.execSQL(sql, new Object[]{"工资", R.drawable.i_gz, R.drawable.i_gz_c, 1});
//        db.execSQL(sql, new Object[]{"奖金", R.drawable.i_jj, R.drawable.i_jj_c, 1});
//        db.execSQL(sql, new Object[]{"借入", R.drawable.i_jr, R.drawable.i_jr_c, 1});
//        db.execSQL(sql, new Object[]{"利息", R.drawable.i_lx, R.drawable.i_lx_c, 1});
//        db.execSQL(sql, new Object[]{"投资", R.drawable.i_tz, R.drawable.i_tz_c, 1});
//        db.execSQL(sql, new Object[]{"二手交易", R.drawable.i_es, R.drawable.i_es_c, 1});
//        db.execSQL(sql, new Object[]{"意外", R.drawable.i_yw, R.drawable.i_yw_c, 1});
//    }

}
