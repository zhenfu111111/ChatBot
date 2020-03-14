package com.example.chatbot.bean;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * 创建日期：
 * <p>
 * 描述：
 */
public class MyApplication extends Application {

    private DbManager db;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);

        initDB();
    }

    public DbManager getDbManage(){
        return db;
    }

    private void initDB() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("myDB")//设置数据库名
                .setDbVersion(1)//设置数据库版本
                .setDbOpenListener(new DbManager.DbOpenListener() {//设置数据库打开监听
                    @Override
                    public void onDbOpened(DbManager db) throws DbException {
                        db.getDatabase().enableWriteAheadLogging();  //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {//数据库升级监听
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) throws DbException {

                    }
                });

        try {
            db = x.getDb(daoConfig);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}

