package com.example.chatbot.bean;

import java.util.Calendar;

/**
 * 创建日期：20200203
 * <p>
 * 描述：返回当前系统时间工具类
 */
public class MyCalendar {
    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;//calendar返回月份从0开始，需要+1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String time=year+"年"+month+"月"+day+"日"+"   "+hour+":"+minute+":"+second;
        return time;
    }
}
