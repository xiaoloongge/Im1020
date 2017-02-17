package com.im1020.modle;

import android.content.Context;

import com.im1020.modle.dao.AccountDao;
import com.im1020.modle.db.AccountDb;
import com.im1020.modle.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/2/14.
 */

public class Modle {

    /*
    * 单例
    *
    * 第一步 私有化构造器
    * 第二步  创建一个静态变量
    * 第三步  创建一个静态的公共方法返回实例
    *
    * */

    private static Modle modle = new Modle();
    private AccountDao accountDao;
    private DBManager dbManager;

    private Modle(){};

    public static Modle getInstance(){
        return modle;
    }


    private Context context;
    public void init(Context context){

        this.context = context;
        //创建AccountDB数据库
        accountDao = new AccountDao(context);
        //初始化全局监听
        new GlobalListener(context);
    }
    /*
    * 线程池分为四种
    * 第一种 缓存线程池 有多少可以开启多少
    * 第二种 定长线程池  固定大小
    * 第三种 调度线程池  可以延时周期执行
    * 第四种  单例线程池  单个
    *
    * */
    private ExecutorService thread = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){

        return thread;
    }

    public AccountDao getAccountDao(){
        return accountDao;
    }


    public void loginSuccess(String currentUser) {

        if (dbManager !=null){
            dbManager.close();
        }
        dbManager = new DBManager(context, currentUser + ".db");

    }

    public DBManager getDbManager(){
        return dbManager;
    }
}
