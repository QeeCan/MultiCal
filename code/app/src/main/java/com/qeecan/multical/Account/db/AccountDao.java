package com.qeecan.multical.Account.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qeecan.multical.Account.bean.AccountBean;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    private AccountSQLiteHelper aHelper;
    private SQLiteDatabase db;
    private static final String[] columns = {
            AccountSQLiteHelper.ID,
            AccountSQLiteHelper.TYPENAME,
            AccountSQLiteHelper.MONEY,
            AccountSQLiteHelper.REMARK,
            AccountSQLiteHelper.TIME,
            AccountSQLiteHelper.KIND
    };

    public AccountDao() { }

    public AccountDao(Context context) {
        aHelper = new AccountSQLiteHelper(context);
    }

    //打开数据库进行操作
    public void open(){
        db = aHelper.getWritableDatabase();
    }

    //关闭数据库操作
    public void close(){
        aHelper.close();
    }

    //增加方法
    public AccountBean addAccount(AccountBean account){
        ContentValues values = new ContentValues();
        values.put(AccountSQLiteHelper.TYPENAME, account.getTypename());
        values.put(AccountSQLiteHelper.MONEY,account.getMoney());
        values.put(AccountSQLiteHelper.REMARK, account.getRemark());
        values.put(AccountSQLiteHelper.TIME, account.getTime());
        values.put(AccountSQLiteHelper.KIND, account.getKind());
        long insertId = db.insert(AccountSQLiteHelper.TABLE_NAME, null, values);
        account.setId(insertId);
        return account;

    }

    //  通过自增长id定位,得到具体的某条记录
    public AccountBean getAccount(long id){

        //游标定位
        Cursor cursor = db.query(AccountSQLiteHelper.TABLE_NAME, columns, AccountSQLiteHelper.ID+ "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        AccountBean e = new AccountBean(cursor.getString(1), cursor.getInt(2),cursor.getString(3), cursor.getString(4), cursor.getInt(5));

        return e;
    }

    //得到全部，使用游标遍历
    public List<AccountBean> getAllAccount(){
        Cursor cursor = db.query(AccountSQLiteHelper.TABLE_NAME,columns,null,null,null, null, null);

        List<AccountBean> accounts = new ArrayList<>();
        //使用前判断其是否有内容
        if(cursor.getCount() > 0){
            //循环遍历Cursor对象，获取下一行是否有内容，如果有，进行获取
            while(cursor.moveToNext()){
                AccountBean account = new AccountBean();
                //获取cursor对象索引为0的数据
                account.setId(cursor.getInt(cursor.getColumnIndex(AccountSQLiteHelper.ID)));
                account.setTypename(cursor.getString(cursor.getColumnIndex(AccountSQLiteHelper.TYPENAME)));
                account.setMoney(cursor.getInt(cursor.getColumnIndex(AccountSQLiteHelper.MONEY)));
                account.setTime(cursor.getString(cursor.getColumnIndex(AccountSQLiteHelper.TIME)));
                account.setRemark(cursor.getString(cursor.getColumnIndex(AccountSQLiteHelper.REMARK)));
                account.setKind(cursor.getInt(cursor.getColumnIndex(AccountSQLiteHelper.KIND)));
                accounts.add(account);
            }
        }
        return accounts;
    }

    //更新
    public int updateAccount(AccountBean account) {
        ContentValues values = new ContentValues();
        values.put(AccountSQLiteHelper.TYPENAME, account.getTypename());
        values.put(AccountSQLiteHelper.MONEY,account.getMoney());
        values.put(AccountSQLiteHelper.TIME, account.getTime());
        values.put(AccountSQLiteHelper.REMARK, account.getRemark());
        values.put(AccountSQLiteHelper.KIND, account.getKind());

        // 更新行
        return db.update(AccountSQLiteHelper.TABLE_NAME, values,
                AccountSQLiteHelper.ID + "=?",new String[] { String.valueOf(account.getId())});
    }

    //删除
    public void removeAccount(AccountBean account) {
        //根据id删除
        db.delete(AccountSQLiteHelper.TABLE_NAME, AccountSQLiteHelper.ID + "=" + account.getId(), null);
    }

}
