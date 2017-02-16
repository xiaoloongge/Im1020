package com.im1020.modle.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.im1020.modle.bean.UserInfo;
import com.im1020.modle.db.DBHelpter;
import com.im1020.modle.table.ContactTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class ContactDao {

    private DBHelpter dbHelpter;

    public ContactDao(DBHelpter dbHelpter){
        this.dbHelpter = dbHelpter;
    }

    // 获取所有联系人
    public List<UserInfo> getContacts(){
        //获取连接
        SQLiteDatabase database = dbHelpter.getReadableDatabase();
        //查询
        String sql = "select * from "+ ContactTable.TABLE_NAME
                + " where "+ContactTable.COL_IS_CONTACT + "=1";
        Cursor cursor = database.rawQuery(sql, null);
        List<UserInfo> userInfos = new ArrayList<>();
        //数据封装
        while (cursor.moveToNext()){
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            userInfos.add(userInfo);
        }
        //关闭cursor
        cursor.close();
        return userInfos;
    }

    // 通过环信id获取联系人单个信息
    public UserInfo getContactByHx(String hxId){
        //校验
        if (TextUtils.isEmpty(hxId)){
            return null;
        }

        //获取连接
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        String sql = "select * from "+ContactTable.TABLE_NAME
                + " where "+ContactTable.COL_USER_HXID + "=?";
        Cursor cursor = database.rawQuery(sql, new String[]{hxId});

        UserInfo userInfo = null;
        if (cursor.moveToNext()){
            userInfo = new UserInfo();
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
        }
        //关闭游标
        cursor.close();
        return userInfo;
    }

    // 通过环信id获取用户联系人信息
    public List<UserInfo> getContactsByHx(List<String> hxIds){

        //校验
        if (hxIds == null || hxIds.size()==0){
            return null;
        }

        //封装数据
        List<UserInfo> userInfos = new ArrayList<>();
        for (String hxid:hxIds) {

            UserInfo userInfo = getContactByHx(hxid);
            if (userInfo != null){
                userInfos.add(userInfo);
            }
        }
        return userInfos;
    }

    // 保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact){
        //校验
        if (user == null){
            return;
        }
        //获取连接
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.COL_USER_HXID,user.getHxid());
        contentValues.put(ContactTable.COL_USER_NAME,user.getUsername());
        contentValues.put(ContactTable.COL_USER_NICK,user.getNick());
        contentValues.put(ContactTable.COL_USER_PHOTO,user.getPhoto());
        contentValues.put(ContactTable.COL_IS_CONTACT,isMyContact?1:0);
        database.replace(ContactTable.TABLE_NAME,null,contentValues);
    }


    // 保存联系人信息
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact){

        //判断
        if (contacts == null || contacts.size()==0){
            return;
        }

        for (UserInfo userinfo: contacts) {
            saveContact(userinfo,isMyContact);
        }

    }

    // 删除联系人信息
    public void deleteContactByHxId(String hxId){
        if (TextUtils.isEmpty(hxId)){
            return;
        }

        //获取连接
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        database.delete(ContactTable.TABLE_NAME,ContactTable.COL_USER_HXID+"=?",new String[]{hxId});
    }
}
