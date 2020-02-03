package com.example.chatbot.model;

/**
 * 创建日期：20200126
 * <p>
 * 描述：model接口，往服务端发送json数据
 */
public interface SentMessageModl {
    /**
     * @param message 用户输入数据
     */
    public void sentMessage(String message);

}
