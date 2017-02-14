package com.im1020.modle.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.im1020.modle.bean.UserInfo;
import com.im1020.modle.db.AccountDb;
import com.im1020.modle.table.AccountTable;

/**
 * Created by Administrator on 2017/2/14.
 */

public class AccountDao {

    private final AccountDb accountDb;

    public AccountDao(Context context){
        //创建Accountdb数据库
        accountDb = new AccountDb(context);
    }

    // 添加用户到数据库
    public void addAccount(UserInfo user){
        //验证
        if (user==null){
            return;
        }

        //获取数据库连接
        SQLiteDatabase database = accountDb.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountTable.COL_USER_HXID,user.getHxid());
        contentValues.put(AccountTable.COL_USER_NAME,user.getUsername());
        contentValues.put(AccountTable.COL_USER_NICK,user.getNick());
        contentValues.put(AccountTable.COL_USER_PHOTO,user.getPhoto());
        database.replace(AccountTable.TABLE_NAME,null,contentValues);
    }

    // 根据环信id获取所有用户信息
    public UserInfo getAccountByHxId(String hxId){

        if (hxId == null || TextUtils.isEmpty(hxId)){
            return null;
        }

        //获取数据库连接
        SQLiteDatabase database = accountDb.getReadableDatabase();

        String sql = "select * from "+AccountTable.TABLE_NAME
                +" where "+AccountTable.COL_USER_HXID+"=?";
        //第二个参数是条件选择
        Cursor cursor = database.rawQuery(sql, new String[]{hxId});

        UserInfo userInfo = null;
        if (cursor.moveToNext()){

            userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_PHOTO)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_NAME)));
        }

        //关闭游标
        cursor.close();

        return userInfo;
    }

}
