package com.example.chatbot.bean;

import java.util.Calendar;

/**
 * 创建日期：20200203
 * <p>
 * 描述：返回当前系统时间工具类
 */
public class MyCalendar {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;
    private final int second;

    public MyCalendar() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        //calendar返回月份从0开始，需要+1
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
    }

    public String getData(){
        String time=year+"年"+month+"月"+day+"日"+"   "+hour+":"+minute+":"+second;
        return time;
    }

    public int getDay(){

        int days=year*10000+month*100+day;
        return days;
    }

    public int getTime(){
        int time = hour * 100 + minute;
        return time;
    }
}
