package com.example.chatbot.bean;

/**
 * 创建日期：20200203
 * <p>
 * 描述：消息类bean，type判断消息类型，0为用户输入，1为服务器返回
 */
public class MessageBean {
    private int type;
    private String message;
    private String date;

    public MessageBean(int type, String message, String date) {
        this.type = type;
        this.message = message;
        this.date = date;
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
}
