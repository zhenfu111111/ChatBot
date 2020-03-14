package com.example.chatbot.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：
 * <p>
 * 描述：
 */
public class DbBean {
    private DbManager dbManager;
    private final SQLiteDatabase mDB;

    public DbBean(DbManager dbManager) {
        this.dbManager=dbManager;
        mDB = this.dbManager.getDatabase();
    }

    public void update(MessageBean messageBean){//增加一条记录
        try {
            dbManager.saveBindingId(messageBean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<MessageBean> selectByDay(int day){//按日期查找
        List<MessageBean> messList=null;
        try {
            messList = dbManager.selector(MessageBean.class).where("day", ">", day-1).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return messList;
    }

    public List<MessageBean> selectAll(){//查找所有记录
        List<MessageBean> all=null;
        try {
            all = dbManager.findAll(MessageBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return all;
    }

    public void deleteAll(){//删除全部记录
        try {
            dbManager.dropTable(MessageBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id){//根据id删除指定记录
        WhereBuilder b=WhereBuilder.b();
        b.and("id","=",id);
        try {
            dbManager.delete(MessageBean.class,b);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public int[] selectAllDay(){//查询记录中所有不同日期
        String sql="select distinct day from messageBean";
        Cursor cursor = mDB.rawQuery(sql, null);
        int[] day=new int[cursor.getCount()];
        if (cursor!=null){
            int i=0;
            while (cursor.moveToNext()){
                int ints=cursor.getInt(0);
                day[i]=ints;
                i++;
            }
        }
        cursor.close();
        return day;
    }



}
