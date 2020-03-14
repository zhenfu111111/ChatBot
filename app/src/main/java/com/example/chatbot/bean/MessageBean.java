package com.example.chatbot.bean;

import androidx.annotation.NonNull;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 创建日期：20200203
 * <p>
 * 描述：消息类bean，type判断消息类型，0为用户输入，1为服务器返回
 */
@Table(name = "messageBean" )
public class MessageBean {
    @Column(name = "id",isId = true,autoGen = true,property = "not null")
    private int id;
    @Column(name = "type")
    private int type;
    @Column(name = "message")
    private String message;
    @Column(name = "date")
    private String date;
    @Column(name = "day")
    private int day;
    @Column(name = "time")
    private int time;



    public MessageBean() {
    }

    public MessageBean(int type, String message, String date, int day,int time) {
        this.type = type;
        this.message = message;
        this.date = date;
        this.day=day;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "messageBean"+"id="+id+"message="+message;
    }
}
